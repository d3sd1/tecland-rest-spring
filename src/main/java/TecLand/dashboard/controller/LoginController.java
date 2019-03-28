package TecLand.dashboard.controller;

import TecLand.ORM.Model.DashUser;
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

import java.sql.Timestamp;

@Controller
public class LoginController {
    @Autowired
    Environment env;

    @Autowired
    private DashUserRepository dashUserRepository;

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    private static final String ENDPOINT = "/dash/login";

    private final SimpMessagingTemplate template;

    private String message = "";

    @Autowired
    public LoginController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping(ENDPOINT)
    public void onReceivedMessage(@Header("sessionId") String sessionId, @Payload DashUserLogin userLogin) {
        Security sec = new Security();
        Response resp = new Response(
                403,
                "ERROR",
                ""
        );

        System.out.println("New login received, checking validity: " + userLogin.getUser().getEmail());
        DashUser dbUser = dashUserRepository.findByEmail(userLogin.getUser().getEmail());
        if (null != dbUser && sec.checkPassword(userLogin.getUser().getPassword(), dbUser.getPassword())) {
            System.out.println("DashUser connected: " + dbUser);

            DashUserLogin login = new DashUserLogin();
            login.setJwt(sec.generateJWTToken(Long.toString(dbUser.getId()), dbUser.getEmail(), "LOGIN",
                    Long.valueOf(this.env.getProperty("tecland.dashboard.session.timeout")), sessionId));
            login.setUser(dbUser);
            login.setCoordsLat(userLogin.getCoordsLat());
            login.setCoordsLng(userLogin.getCoordsLng());
            login.setExpended(new Timestamp(System.currentTimeMillis()));
            login.setExpires((new Timestamp(System.currentTimeMillis() + Integer.parseInt(this.env.getProperty("tecland.dashboard.session.timeout")) * 1000)));

            final DashUserLogin updatedUser = this.dashUserLoginRepository.save(login);

            resp = new Response(
                    200,
                    "SUCCESS",
                    login.getJwt()
            );
        }


        template.convertAndSend(ENDPOINT + "/" + sessionId, resp);
    }


    @SubscribeMapping(ENDPOINT)
    public String onSubscribe() {
        return "SUBSCRIBED : " + message;
    }

}
