package andrey.code.manager.config;

import andrey.code.manager.client.RestClientProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductRestClient productsRestClient(
            @Value("${market.services.rest.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("${market.services.rest.username:rest_service_user}") String restServiceUsername,
            @Value("${market.services.rest.password:password}") String restServicePassword) {
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(
                        new BasicAuthenticationInterceptor(restServiceUsername, restServicePassword))
                .build());
    }
}
