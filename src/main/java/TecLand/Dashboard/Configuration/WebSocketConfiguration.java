package TecLand.Dashboard.Configuration;

import TecLand.Logger.LogService;
import TecLand.ORM.Repository.DashUserLoginRepository;
import TecLand.Utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Autowired
    Environment env;
    @Autowired
    private LogService logger;
    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;
    @Autowired
    private Security sec;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration endpoint = registry.addEndpoint("/opencon");
        if (this.env.getProperty("env.dev").equals("false")) {
            endpoint.setAllowedOrigins("tecland.net");
            endpoint.setAllowedOrigins("*.tecland.net");
        } else {
            endpoint.setAllowedOrigins("*");
        }
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        try {
            registration.interceptors(new SessionInterceptor(logger, dashUserLoginRepository, sec));
        } catch (MessagingException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return false;
    }
}

