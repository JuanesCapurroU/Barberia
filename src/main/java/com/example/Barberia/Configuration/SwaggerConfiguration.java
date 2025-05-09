package com.example.Barberia.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API DE PROYECTO FINAL")
                        .version("1.0")
                        .description("Docuentacion de la api de proyecto final")
                        .contact(new Contact()
                                .name("Juan Capurro, Juan Bustos")
                                .email("juan.capurro@uniminuto.edu.co juan.bustos-ca@uniminuto.edu.co")));
    }
}
