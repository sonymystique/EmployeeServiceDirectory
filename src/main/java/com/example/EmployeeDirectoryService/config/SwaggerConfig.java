package com.example.EmployeeDirectoryService.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerCustom(){
        return  new OpenAPI().info(
                new Info().title("Employee App APIs")
                        .description("by Sony")
        )
                .servers(List.of(new Server().url("http://localhost:8081").description("local"),
                        new Server().url("http://localhost:8082").description("live")))
         .components(new Components()
                .addSecuritySchemes("basicAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

}
