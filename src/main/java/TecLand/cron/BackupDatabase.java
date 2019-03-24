package TecLand.cron;

import com.smattme.MysqlExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

@Component
public class BackupDatabase {
    @Autowired
    Environment env;

    @Profile("prod")
    @Scheduled(cron = "0 1 1 * * *", zone = "Europe/Madrid")
    public void doScheduledWork() {

        Properties properties = new Properties();
        properties.setProperty(MysqlExportService.DB_NAME, env.getProperty("mysql.dbname"));
        properties.setProperty(MysqlExportService.DB_USERNAME, env.getProperty("mysql.user"));
        properties.setProperty(MysqlExportService.DB_PASSWORD, env.getProperty("mysql.pass"));
        properties.setProperty(MysqlExportService.EMAIL_HOST, env.getProperty("smtp.host"));
        properties.setProperty(MysqlExportService.EMAIL_PORT, env.getProperty("smtp.port"));
        properties.setProperty(MysqlExportService.EMAIL_USERNAME, env.getProperty("smtp.user"));
        properties.setProperty(MysqlExportService.EMAIL_PASSWORD, env.getProperty("smtp.pass"));
        properties.setProperty(MysqlExportService.EMAIL_FROM, env.getProperty("smtp.backup.from"));
        properties.setProperty(MysqlExportService.EMAIL_TO, env.getProperty("tecland.sysadmin.email"));
        properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");


        properties.setProperty(MysqlExportService.TEMP_DIR, new File(env.getProperty("backup.db.path")).getPath());

        MysqlExportService mysqlExportService = new MysqlExportService(properties);
        File file = mysqlExportService.getGeneratedZipFile();

        try {
            mysqlExportService.export();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
