package TecLand.Dashboard.Configuration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;

import java.security.Principal;
import java.util.List;

public class FilterChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())
                || StompCommand.SEND.equals(headerAccessor.getCommand())) {
            // Your logic. VEWRIFY HEADERS HERE
        }

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        List tokenList = accessor.getNativeHeader("jwt_session");
        String token;
        if (tokenList == null || tokenList.size() < 1) {
            return message;
        } else {
            token = tokenList.get(0).toString();
        }

        // validate and convert to a Principal based on your own requirements e.g.
        // authenticationManager.authenticate(JwtAuthentication(token))
        Principal yourAuth = new Principal() {
            @Override
            public String getName() {
                return "MY NEW GUACHI NAME";
            }
        };

        accessor.setUser(yourAuth);

        // not documented anywhere but necessary otherwise NPE in StompSubProtocolHandler!
        accessor.setLeaveMutable(true);

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel messageChannel, boolean b) {

    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

    }

    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        return false;
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        return null;
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

    }
}