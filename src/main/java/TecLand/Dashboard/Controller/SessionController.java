package TecLand.Dashboard.Controller;

import TecLand.Dashboard.Annotation.Permission;
import TecLand.Dashboard.RestEndpoint.DashRestRoute;
import TecLand.Dashboard.Services.DashSession;
import TecLand.Logger.LogService;
import TecLand.ORM.Dashboard.DashUser;
import TecLand.ORM.Dashboard.DashUserLogin;
import TecLand.ORM.Repository.DashUserLoginHistoricalRepository;
import TecLand.ORM.Repository.DashUserLoginRepository;
import TecLand.ORM.Repository.DashUserRepository;
import TecLand.Utils.RestResponse;
import TecLand.Utils.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

@Controller
public class SessionController {
    @Autowired
    private LogService logger;

    @Autowired
    Environment env;

    @Autowired
    private DashUserRepository dashUserRepository;

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    @Autowired
    private Security sec;

    @Autowired
    private DashUserLoginHistoricalRepository dashUserLoginHistoricalRepository;

    @Autowired
    DashSession dashSessionManager;

    private final SimpMessagingTemplate broadcaster;

    @Autowired
    public SessionController(SimpMessagingTemplate template) {
        this.broadcaster = template;
    }


    @MessageMapping(DashRestRoute.LOGOUT)
    @SendToUser(DashRestRoute.LOGOUT)
    @Permission()
    public RestResponse onLogout(@Payload DashUserLogin userLogin, SimpMessageHeaderAccessor headerAccessor) {
        RestResponse resp = new RestResponse(
                403,
                "ERROR",
                ""
        );


        this.logger.info("New logout received, checking validity: " + userLogin.getJwt());
        DashUserLogin dbUserLogin = dashUserLoginRepository.findByJwt(userLogin.getJwt());
        if (null != dbUserLogin) {
            /* Move login to historical */
            this.dashSessionManager.moveLoginToHistorical(dbUserLogin);

            resp = new RestResponse(
                    200,
                    "SUCCESS",
                    ""
            );
        }

        return resp;
    }

    @MessageMapping(DashRestRoute.LOGIN)
    @SendToUser(DashRestRoute.LOGIN)
    @Permission()
    public RestResponse onLogin(@Payload DashUserLogin userLogin, SimpMessageHeaderAccessor headerAccessor) {
        RestResponse resp = new RestResponse(
                403,
                "ERROR",
                ""
        );


        this.logger.info("New login received, checking validity: " + userLogin.getDashUser().getEmail());
        DashUser dbUser = dashUserRepository.findByEmail(userLogin.getDashUser().getEmail());
        if (null != dbUser && sec.checkPassword(userLogin.getDashUser().getPassword(), dbUser.getPassword())) {
            /* Move previous logins to historical */
            /* Generate new login */
            this.logger.info("DashUser connected: " + dbUser);

            String sessionHash = sec.buildSessionHash(dbUser.getId());
            DashUserLogin login = new DashUserLogin();
            login.setJwt(sec.generateJWTToken(Long.toString(dbUser.getId()), dbUser.getEmail(), "LOGIN",
                    Long.valueOf(this.env.getProperty("tecland.dashboard.session.timeout")), sessionHash));
            login.setHash(sessionHash);
            login.setDashUser(dbUser);
            login.setCoordsLat(userLogin.getCoordsLat());
            login.setCoordsLng(userLogin.getCoordsLng());
            login.setExpended(new Timestamp(System.currentTimeMillis()));
            login.setExpires((new Timestamp(System.currentTimeMillis() + Integer.parseInt(this.env.getProperty("tecland.dashboard.session.timeout")) * 1000)));

            this.dashSessionManager.setNewLogin(login);
            this.dashUserLoginRepository.save(login);

            resp = new RestResponse(
                    200,
                    "SUCCESS",
                    login
            );

            /* Disconnect other users that are in other JWT sessions */
            this.broadcaster.convertAndSend(DashRestRoute.CHECK_SESSION, new RestResponse(
                    102,
                    "NEWCON",
                    dbUser.getId()
            ));
        }

        return resp;
    }

    @MessageMapping(DashRestRoute.SESSION_DATA)
    @SendToUser(DashRestRoute.SESSION_DATA)
    @Permission()
    public RestResponse onSessiondata(@Payload DashUser userUpdate, SimpMessageHeaderAccessor headerAccessor) {
        RestResponse resp = new RestResponse(
                403,
                "ERROR",
                ""
        );
        DashUser user = (DashUser) headerAccessor.getSessionAttributes().get("user");
        user = this.dashUserRepository.findByEmail(user.getEmail());
        /* Update user if wanted */
        if (null != userUpdate) {
            if (userUpdate.getLastVisitPage() != null) {
                user.setLastVisitPage(userUpdate.getLastVisitPage());
            }
            this.dashUserRepository.save(user);
        }

        /* Return user if needed */
        if (null != user) {
            resp.setStatusCode(200);
            resp.setStatusMessage("DATA_RETRIEVED");
            resp.setData(user);
        }
        return resp;
    }
}