package TecLand.Dashboard.Configuration;

import TecLand.Dashboard.RestEndpoint.DashRestRoute;
import TecLand.Logger.LogService;
import TecLand.ORM.Dashboard.DashUserLogin;
import TecLand.ORM.Repository.DashUserLoginRepository;
import TecLand.Utils.Security;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.List;
import java.util.Map;

//TODO: esta clase lanza una excepcion cuando te conectas desde otro navefgador (teniendo el naveghador actuial cerrado). esta excepcion es verbose, y se deberia eliminar

public class SessionInterceptor implements ChannelInterceptor {
    private LogService logger;
    private DashUserLoginRepository dashUserLoginRepository;
    private Security sec;

    public SessionInterceptor(LogService logger, DashUserLoginRepository dashUserLoginRepository, Security sec) {
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
        }
        if (pathList == null || pathList.size() < 1) {
            throw new MessagingException("PATH_NOT_FOUND");
        } else {
            path = pathList.get(0).toString();
        }
        boolean currentPathRequiresLogin = !path.contains(DashRestRoute.LOGIN_NOT_NEEDED_PREFIX) && !path.contains(DashRestRoute.ROUTER);
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

        if (null != login) {
            Map<String, Object> attrs = accessor.getSessionAttributes();
            attrs.put("user", login.getDashUser());
            accessor.setSessionAttributes(attrs);
        }

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