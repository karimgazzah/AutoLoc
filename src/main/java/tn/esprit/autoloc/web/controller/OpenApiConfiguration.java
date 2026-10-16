package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI autoLocOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("AutoLoc API")
                        .version("1.0")
                        .description("API REST de gestion de location de véhicules."));
    }
}
