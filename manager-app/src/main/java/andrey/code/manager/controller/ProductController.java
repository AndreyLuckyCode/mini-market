package andrey.code.manager.controller;

import andrey.code.manager.client.RestClientProductRestClient;
import andrey.code.manager.controller.payload.ProductPayload;
import andrey.code.manager.entity.ProductDTO;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("products")
public class ProductController {

    RestClientProductRestClient restClientProductRestClient;

    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductPayload payload,
                                                BindingResult bindingResult)
            throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        return restClientProductRestClient.createProduct(payload);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return restClientProductRestClient.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById (@PathVariable ("productId") Long id){
        ResponseEntity<Optional<ProductDTO>> response = restClientProductRestClient.getProductById(id);
        return response.getBody().map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> updateProduct (@PathVariable ("productId") Long id,
                                                 @Valid @RequestBody ProductPayload payload,
                                                 BindingResult bindingResult)
            throws BindException {

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        return restClientProductRestClient.updateProduct(id, payload);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id){
        return restClientProductRestClient.deleteProduct(id);
    }
}
