package TecLand.dashboard.controller;

import TecLand.ORM.Model.DashUserLogin;
import TecLand.ORM.Repository.DashUserLoginRepository;
import TecLand.ORM.Repository.DashUserRepository;
import TecLand.model.Response;
import TecLand.utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class JWTSessionController {
    @Autowired
    Environment env;

    @Autowired
    private DashUserRepository dashUserRepository;

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    private static final String ENDPOINT = "/dash/jwt/session";

    private final SimpMessagingTemplate template;

    private String message = "";

    @Autowired
    public JWTSessionController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(ENDPOINT)
    public void onReceivedMessage(@Header("sessionId") String sessionId, @Payload DashUserLogin userLogin) {
        Security sec = new Security();
        Response resp = new Response(
                403,
                "ERROR",
                "NOT FOUND"
        );

        System.out.println("New check received, checking validity: " + userLogin.getJwt());
        DashUserLogin dbUserLogin = dashUserLoginRepository.findByJwt(userLogin.getJwt());
        if (null != dbUserLogin) {
            System.out.println("DashUser session retrieved: " + dbUserLogin);
            if (true) {
                //TODO: que no coja los expirados.
            }
            resp = new Response(
                    200,
                    "SUCCESS",
                    ""
            );
        }


        template.convertAndSend(ENDPOINT + "/" + sessionId, resp);
    }


    @SubscribeMapping(ENDPOINT)
    public String onSubscribe() {
        return "SUBSCRIBED : " + message;
    }

}
