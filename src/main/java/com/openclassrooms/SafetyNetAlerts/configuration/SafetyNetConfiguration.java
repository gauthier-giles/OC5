package com.openclassrooms.SafetyNetAlerts.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;

    @Configuration
    public class SafetyNetConfiguration {
        @Bean
        public HttpTraceRepository httpTraceRepository()
        {
            return new InMemoryHttpTraceRepository();
        }
    }

