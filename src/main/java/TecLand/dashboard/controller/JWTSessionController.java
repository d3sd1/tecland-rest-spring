package TecLand.dashboard.controller;

import TecLand.Logger.LogService;
import TecLand.ORM.Model.DashUserLogin;
import TecLand.ORM.Model.DashUserLoginHistorical;
import TecLand.ORM.Repository.DashUserLoginHistoricalRepository;
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
    private LogService logger;

    @Autowired
    Environment env;

    @Autowired
    private DashUserRepository dashUserRepository;

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    @Autowired
    private DashUserLoginHistoricalRepository dashUserLoginHistoricalRepository;

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
        if (null == userLogin) {
            userLogin = new DashUserLogin();
        }

        this.logger.info("New check received, checking validity: " + userLogin.getJwt());
        DashUserLogin dbUserLogin = dashUserLoginRepository.findByJwt(userLogin.getJwt());
        if (null != dbUserLogin) {
            this.logger.info("DashUser session retrieved: " + dbUserLogin);
            if (!sec.isJWTExpired(dbUserLogin.getJwt(), dbUserLogin.getHash())) {
                resp = new Response(
                        200,
                        "SUCCESS",
                        ""
                );
            }
        } else { // Check login historiccals thathas not expired, then, return a code to show "Connected from another device"
            DashUserLoginHistorical dbHistoricalUserLogin = dashUserLoginHistoricalRepository.findByJwt(userLogin.getJwt());
            if (!sec.isJWTExpired(dbHistoricalUserLogin.getJwt(), dbHistoricalUserLogin.getHash())) {
                resp = new Response(
                        401,
                        "LOGIN_SMW_ELSE",
                        ""
                );
            }
        }

        template.convertAndSend(ENDPOINT + "/" + sessionId, resp);
    }


    @SubscribeMapping(ENDPOINT)
    public String onSubscribe() {
        return "SUBSCRIBED : " + message;
    }

}
