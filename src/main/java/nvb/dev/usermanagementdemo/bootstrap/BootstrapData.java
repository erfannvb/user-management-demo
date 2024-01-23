package nvb.dev.usermanagementdemo.bootstrap;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import nvb.dev.usermanagementdemo.config.InfoConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log
public class BootstrapData implements CommandLineRunner {

    private final InfoConfig infoConfig;

    @Override
    public void run(String... args) throws Exception {
        log.info(
                String.format("This app is made by %s in %d, this is version %.1f",
                        infoConfig.getMaker(),
                        infoConfig.getYear(),
                        infoConfig.getVersion()
                ));
    }
}
