package com.example.EmployeeDirectoryService.auditing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        return new SpringSecurityAuditorAware();
    }
}
