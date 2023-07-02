package com.techChallenge.lanchonete.adapter.infra.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
    public class SwaggerConfig {

        @Bean
        public OpenAPI lanchoneteOpenAPI() {
            return new OpenAPI()
                    .info(new Info().title("API TECH-CHALLENGE Lanchonete ")

                            .description("Esta API é utilizada pelo Sistema Lanchonete desenvolvido para a Pós Graduação FIAP")

                            .version("v0.0.1").contact(new Contact()

                                    .name("Henrique Marchioni | Wesley Martins")
                                    .email("henrique.marchioni@fatec.sp.gov.br")).
                            license(new License().name("Apache 2.0").url("http://springdoc.org")));

        }

}
