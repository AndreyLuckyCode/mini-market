package andrey.code.manager.client;

import andrey.code.manager.controller.payload.ProductPayload;
import andrey.code.manager.entity.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestClientProductRestClient implements ProductRestClient{

    RestClient restClient;

    @Override
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload) {

        return restClient
                .post()
                .uri("/api/v1//products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(payload)
                .retrieve()
                .toEntity(String.class);
    }

    @Override
    public  ResponseEntity<List<ProductDTO>> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<ProductDTO> getProductById (@PathVariable ("productId") Long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                                @RequestBody ProductPayload payload) {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id) {
        return null;
    }
}
