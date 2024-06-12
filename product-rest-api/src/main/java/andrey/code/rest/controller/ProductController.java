package andrey.code.rest.controller;

import andrey.code.rest.controller.payload.ProductPayload;
import andrey.code.rest.mapper.ProductDTO;
import andrey.code.rest.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1")
public class ProductController {

    ProductService productService;

    private static final String CREATE_PRODUCT = "/products";
    private static final String GET_PRODUCT = "/products/{productId}";
    private static final String GET_PRODUCTS_LIST = "/products";
    private static final String UPDATE_PRODUCT = "/products/{productId}";
    private static final String DELETE_PRODUCT = "/products/{productId}";

    @PostMapping(CREATE_PRODUCT)
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload){
        return productService.createProduct(payload);
    }

    @GetMapping(GET_PRODUCT)
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long id){
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

    @GetMapping(GET_PRODUCTS_LIST)
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return productService.getAllProducts();
    }

    @PatchMapping(UPDATE_PRODUCT)
    public ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                                @RequestBody ProductPayload payload){
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping(DELETE_PRODUCT)
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long id){
        return productService.deleteProduct(id);
    }
}
