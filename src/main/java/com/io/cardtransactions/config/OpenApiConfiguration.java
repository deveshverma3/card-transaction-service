package com.io.cardtransactions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class OpenApiConfiguration {

    @Bean
    public OpenAPI cardTransactionsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Card Transactions API")
                        .description("API for managing accounts and transactions")
                        .version("v1.0"));
    }
}
