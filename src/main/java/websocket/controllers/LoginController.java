package websocket.controllers;

import model.Response;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import utils.Security;


@Controller
public class LoginController {
    private static final String ENDPOINT = "/login";

    private final SimpMessagingTemplate template;

    private String message = "";

    @Autowired
    public LoginController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(ENDPOINT)
    public void onReceivedMessage(@Header("sessionId") String sessionId, @Payload User user) {
        Security sec = new Security();
        Response resp = new Response(
                403,
                "ERROR",
                ""
        );
        //TODO: configure session timeout
        user.setLoginJWT(sec.generateJWTToken(Long.toString(user.getId()), user.getEmail(), "LOGIN", 3600, "generatehashere"));
        System.out.println("New login received: " + user);

        // TODO: validate against db
        if (user.getEmail().equals("admin@tecland.net") && user.getPassword().equals("admin")) {
            System.out.println("User connected: " + user);

            // TODO: orm flush
            resp = new Response(
                    200,
                    "SUCCESS",
                    user.getLoginJWT()
            );
        }
        template.convertAndSend(ENDPOINT + "/" + sessionId, resp);
    }


    @SubscribeMapping(ENDPOINT)
    public String onSubscribe() {
        return "SUBSCRIBED : " + message;
    }

}
