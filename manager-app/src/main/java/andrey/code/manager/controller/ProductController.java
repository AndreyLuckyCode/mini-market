package andrey.code.manager.controller;

import andrey.code.manager.client.RestClientProductRestClient;
import andrey.code.manager.controller.payload.ProductPayload;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("products")
public class ProductController {

    RestClientProductRestClient restClientProductRestClient;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload){

        return restClientProductRestClient.createProduct(payload);
    }


}
