package TecLand.Dashboard.Annotation;

import TecLand.Logger.LogService;
import TecLand.ORM.Model.DashPermission;
import TecLand.ORM.Model.DashUser;
import TecLand.ORM.Repository.DashPermissionRepository;
import TecLand.Utils.RestResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private DashPermissionRepository dashPermissionRepository;

    @Autowired
    private LogService logger;

    @Around("@annotation(Permission)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget()
                .getClass()
                .getMethod(signature.getMethod().getName(),
                        signature.getMethod().getParameterTypes());
        Permission monitored = method.getAnnotation(Permission.class);

        Object[] args = joinPoint.getArgs();
        SimpMessageHeaderAccessor headerAccessor = null;
        for (Object arg : args) {
            if (arg.getClass().toString().toLowerCase().contains("stompheaderaccessor")) {
                headerAccessor = (SimpMessageHeaderAccessor) arg;
                break;
            }
        }

        if (null == headerAccessor) {
            logger.error("You must put SimpMessageHeaderAccessor headerAccessor as a param if you wanna use @Permission annotation.");
            return new RestResponse(
                    500,
                    "ERROR_MISSCONFIGURED_CONTROLLER",
                    ""
            );
        }

        for (String permission : monitored.value()) {
            DashPermission dp = this.dashPermissionRepository.findByPermissionKey(permission);
            if (null == dp) {
                logger.warning("Permission not found on database (do you need to add it to migrations?): " + permission);
                continue;
            }
            DashUser du = (DashUser) headerAccessor.getSessionAttributes().get("user");
            logger.debug("Permission (" + permission + ") not found on user: " + du.getId());
            if (null == du || !du.getPermissions().contains(dp)) {
                return new RestResponse(
                        401,
                        "ERROR_PERMISSIONS",
                        ""
                );
            }
        }
        return joinPoint.proceed();
    }
}
