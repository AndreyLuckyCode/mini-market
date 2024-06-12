package andrey.code.manager.client;

import andrey.code.manager.controller.payload.ProductPayload;
import andrey.code.manager.entity.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestClientProductRestClient implements ProductRestClient{

    RestClient restClient;

    private static final String URI_CONST = "/api/v1/products/{productId}";

    private static final ParameterizedTypeReference<List<ProductDTO>> PRODUCT_DTO_LIST_TYPE_REFERENCE
            = new ParameterizedTypeReference<List<ProductDTO>>() {};


    @Override
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload) {
        try {
            return restClient
                    .post()
                    .uri("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = restClient
                .get()
                .uri("/api/v1/products")
                .retrieve()
                .body(PRODUCT_DTO_LIST_TYPE_REFERENCE);

        return ResponseEntity.ok(products);
    }

    @Override
    public ResponseEntity<Optional<ProductDTO>> getProductById (@PathVariable ("productId") Long id) {
        try {
            ProductDTO product =
                    restClient
                            .get()
                            .uri(URI_CONST, id)
                            .retrieve()
                            .body(ProductDTO.class);

            return ResponseEntity.ok(Optional.of(product));
        } catch (HttpClientErrorException.NotFound exception) {
            return ResponseEntity.ok(Optional.empty());
        }
    }

    @Override
    public ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                                @RequestBody ProductPayload payload) {
        try {
            return restClient
                    .patch()
                    .uri(URI_CONST, id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        }
    }

    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id) {
        try {
            return restClient
                    .delete()
                    .uri(URI_CONST, id)
                    .retrieve()
                    .toEntity(String.class);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
