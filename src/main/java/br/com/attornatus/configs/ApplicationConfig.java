package br.com.attornatus.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ApplicationConfig {

    @Bean
    public ZoneId zoneId() {
        return ZoneId.of("America/Sao_Paulo");
    }

    @Bean
    public Clock clock() {
        return Clock.system(zoneId());
    }

}
