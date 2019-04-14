package TecLand.CRON.ScrapperMobile;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MovilZona {

    @Profile({"dev","test"})
    @Scheduled(fixedDelay = 5000)//Every 5000ms or 5s
    public void ScrapperMovilZona(){
    }
}
