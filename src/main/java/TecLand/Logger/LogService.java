package TecLand.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("LogService")
@Transactional
public class LogService {
    private LogRepository logRepository;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    private Class<?> getCaller() {
        try {
            Class<?> clazz = this.getClass();
            for (StackTraceElement trace : Thread.currentThread().getStackTrace()) {
                if (trace.getClassName().indexOf("TecLand.") != -1 && trace.getClassName().indexOf("TecLand.Logger") == -1) {
                    clazz = Class.forName(trace.getClassName());
                    break;
                }
            }
            return clazz;
        } catch (Exception e) {
            Logger consoleLogger = LoggerFactory.getLogger(this.getClass());
            consoleLogger.error("CRITICAL ERROR ON LOG SERVICE!!! CHECK BELOW");
            e.printStackTrace();
            return this.getClass();
        }
    }

    public void debug(String msg) {
        // This won't debug to MongoDB since we don't want to saturate it lol.
        if (this.activeProfile.equals("dev") || this.activeProfile.equals("test")) {
            Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
            consoleLogger.info(msg);
        }
    }

    public void info(String msg) {
        if (this.activeProfile.equals("dev") || this.activeProfile.equals("test")) {
            Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
            consoleLogger.info(msg);
        }
        Log log = new Log();
        log.setLevel(LogLevel.INFO);
        log.setText(msg);
        this.logRepository.saveLog(log);
    }

    public void warning(String msg) {
        if (this.activeProfile.equals("dev") || this.activeProfile.equals("test")) {
            Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
            consoleLogger.warn(msg);
        }
        Log log = new Log();
        log.setLevel(LogLevel.WARNING);
        log.setText(msg);
        this.logRepository.saveLog(log);
    }

    public void error(String msg) {
        if (this.activeProfile.equals("dev") || this.activeProfile.equals("test")) {
            Logger consoleLogger = LoggerFactory.getLogger(this.getCaller());
            consoleLogger.error(msg);
        }
        Log log = new Log();
        log.setLevel(LogLevel.ERROR);
        log.setText(msg);
        this.logRepository.saveLog(log);
    }

}
