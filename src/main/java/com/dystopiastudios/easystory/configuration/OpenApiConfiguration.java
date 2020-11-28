package com.dystopiastudios.easystory.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfiguration {
    @Bean(name = "easystoryOpenApi")
    public OpenAPI easystoryOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Authorization",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("EasyStory Application API")
                        .description("EasyStory API implemented with Spring Boot RESTful service and documented using springdoc-openapi and OpenAPI 3.0"));
    }

}
