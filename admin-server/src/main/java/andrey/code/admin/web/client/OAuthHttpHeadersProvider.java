package andrey.code.admin.web.client;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OAuthHttpHeadersProvider implements HttpHeadersProvider {

    OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;

    @Override
    public HttpHeaders getHeaders(Instance instance) {
        OAuth2AuthorizedClient authorizedClient = this.oAuth2AuthorizedClientManager.authorize(
                OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                        .principal("admin-service")
                        .build());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());
        return httpHeaders;
    }
}