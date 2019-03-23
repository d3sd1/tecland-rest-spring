package TecLand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Application implements ApplicationListener<ApplicationReadyEvent> {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("Checking active profiles...");
        for (String prof : event.getApplicationContext().getEnvironment().getActiveProfiles()) {
            System.out.println("Active profile: " + prof);
        }

    }

}
