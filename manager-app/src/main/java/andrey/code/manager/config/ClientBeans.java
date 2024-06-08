package andrey.code.manager.config;

import andrey.code.manager.client.RestClientProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductRestClient productsRestClient(
            @Value("${market.services.rest.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}
