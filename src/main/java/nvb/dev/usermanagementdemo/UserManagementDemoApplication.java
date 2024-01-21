package nvb.dev.usermanagementdemo;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import nvb.dev.usermanagementdemo.config.InfoConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Log
public class UserManagementDemoApplication implements CommandLineRunner {

    private final InfoConfig infoConfig;

    public static void main(String[] args) {
        SpringApplication.run(UserManagementDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(String.format("This app is made by %s in %d, this is version %s",
                infoConfig.getMaker(),
                infoConfig.getYear(),
                infoConfig.getVersion()));
    }
}
