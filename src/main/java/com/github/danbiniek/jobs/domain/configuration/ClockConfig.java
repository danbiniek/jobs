package com.github.danbiniek.jobs.domain.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    @Bean
    public Clock defaultClock() {
        return Clock.systemDefaultZone();
    }

}
