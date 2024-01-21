package nvb.dev.usermanagementdemo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoConfig {

    private String maker;
    private int year;
    private double version;

}
