package TecLand.Dashboard.Configuration;

import TecLand.Dashboard.RestEndpoint.DashRestRoute;
import TecLand.Logger.LogService;
import TecLand.ORM.Model.DashUserLogin;
import TecLand.ORM.Repository.DashUserLoginRepository;
import TecLand.Utils.Security;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;
import java.util.List;

public class FilterChannelInterceptor implements ChannelInterceptor {
    private LogService logger;
    private DashUserLoginRepository dashUserLoginRepository;
    private Security sec;

    public FilterChannelInterceptor(LogService logger, DashUserLoginRepository dashUserLoginRepository, Security sec) {
        this.logger = logger;
        this.dashUserLoginRepository = dashUserLoginRepository;
        this.sec = sec;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);


        /* Only check SUBSCRIBE and SEND operations */
        if (!StompCommand.SUBSCRIBE.equals(accessor.getCommand())
                && !StompCommand.SEND.equals(accessor.getCommand())) {
            return message;
        }

        /* Check if user has JWT, and it is required for current path */
        List tokenList = accessor.getNativeHeader("jwt_session");
        List pathList = accessor.getNativeHeader("destination");
        String jwtToken, path;
        if (tokenList == null || tokenList.size() < 1) {
            throw new MessagingException("DISCONNECT_SILENTLY");
        } else {
            jwtToken = tokenList.get(0).toString();
            System.out.println("JWT: -----------> " + jwtToken);
        }
        if (pathList == null || pathList.size() < 1) {
            throw new MessagingException("PATH_NOT_FOUND");
        } else {
            path = pathList.get(0).toString();
        }
        boolean currentPathRequiresLogin = !path.contains(DashRestRoute.LOGIN_NOT_NEEDED_PREFIX);
        DashUserLogin login = this.dashUserLoginRepository.findByJwt(jwtToken);
        boolean validJwtSession = false;
        if (null != login && !this.sec.isJWTExpired(login.getJwt(), login.getHash())) {
            validJwtSession = true;
        }

        if (!validJwtSession && !currentPathRequiresLogin && !jwtToken.equals("")) {
            throw new MessagingException("DISCONNECT_SILENTLY");
        } else if (!validJwtSession && !jwtToken.equals("")) {
            throw new MessagingException("DISCONNECT");
        } else if (currentPathRequiresLogin && !validJwtSession) {
            throw new MessagingException("NO_PERMISSION");
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