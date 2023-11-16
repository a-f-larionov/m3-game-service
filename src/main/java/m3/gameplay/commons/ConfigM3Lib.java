package m3.gameplay.commons;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(value = "m3.lib")
@EntityScan(value = "m3.lib")
@EnableJpaRepositories(value = "m3.lib")
@Configuration
public class ConfigM3Lib {
}
