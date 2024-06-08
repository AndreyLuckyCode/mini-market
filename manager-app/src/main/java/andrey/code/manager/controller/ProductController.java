package andrey.code.manager.controller;

import andrey.code.manager.client.RestClientProductRestClient;
import andrey.code.manager.controller.payload.ProductPayload;
import andrey.code.manager.entity.ProductDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){

        return restClientProductRestClient.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById (@PathVariable ("productId") Long id){

        return restClientProductRestClient.getProductById(id);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> updateProduct (@PathVariable ("productId") Long id,
                                                 @RequestBody ProductPayload payload){

        return restClientProductRestClient.updateProduct(id, payload);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id){

        return restClientProductRestClient.deleteProduct(id);
    }
}
