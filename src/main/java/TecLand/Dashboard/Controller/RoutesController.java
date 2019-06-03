package TecLand.Dashboard.Controller;

import TecLand.Dashboard.Annotation.Permission;
import TecLand.Dashboard.RestEndpoint.DashRestRoute;
import TecLand.Logger.LogService;
import TecLand.Utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class RoutesController {
    private final SimpMessagingTemplate broadcaster;
    @Autowired
    Environment env;
    @Autowired
    private LogService logger;

    @Autowired
    public RoutesController(SimpMessagingTemplate template) {
        this.broadcaster = template;
    }


    @MessageMapping(DashRestRoute.ROUTER)
    @SendToUser(DashRestRoute.ROUTER)
    @Permission()
    public RestResponse onRouting(SimpMessageHeaderAccessor headerAccessor) {
        RestResponse resp = new RestResponse(
                200,
                "HOLAHOLA",
                "LAS RUTAS LAS TENGO YO "
        );

        //TODO: falta que salga este output por consola, devolver las rutas y guardarlas en un array en el cliente.
        //Ruta: id, UNIQUE_NAME, ruta (path), privada

        System.out.println("ROUTES AR HER");
        this.logger.info("Routes wanted");

        return resp;
    }
}