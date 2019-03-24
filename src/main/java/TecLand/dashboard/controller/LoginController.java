package TecLand.dashboard.controller;

import TecLand.ORM.DashUser;
import TecLand.dashboard.repository.UserRepository;
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
public class LoginController {
    @Autowired
    Environment env;

    @Autowired
    private UserRepository userRepository;
    private static final String ENDPOINT = "/dash/login";

    private final SimpMessagingTemplate template;

    private String message = "";

    @Autowired
    public LoginController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(ENDPOINT)
    public void onReceivedMessage(@Header("sessionId") String sessionId, @Payload DashUser user) {
        Security sec = new Security();
        Response resp = new Response(
                403,
                "ERROR",
                ""
        );

        System.out.println("New login received, checking validity: " + user.getEmail());
        DashUser dbUser = userRepository.findByEmail(user.getEmail());
        if (null != dbUser && sec.checkPassword(user.getPassword(), dbUser.getPassword())) {
            System.out.println("DashUser connected: " + dbUser);

            dbUser.setLoginJWT(sec.generateJWTToken(Long.toString(dbUser.getId()), dbUser.getEmail(), "LOGIN",
                    Long.valueOf(this.env.getProperty("tecland.dashboard.session.timeout")), sessionId));
            final DashUser updatedUser = userRepository.save(dbUser);

            resp = new Response(
                    200,
                    "SUCCESS",
                    dbUser.getLoginJWT()
            );
        }

        template.convertAndSend(ENDPOINT + "/" + sessionId, resp);
    }


    @SubscribeMapping(ENDPOINT)
    public String onSubscribe() {
        return "SUBSCRIBED : " + message;
    }

}
