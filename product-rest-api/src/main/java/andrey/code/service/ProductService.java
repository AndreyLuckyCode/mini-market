package andrey.code.service;

import andrey.code.controller.payload.ProductPayload;
import andrey.code.mapper.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {

    ResponseEntity<String> createProduct (@RequestBody ProductPayload payload);

    ResponseEntity<List<ProductDTO>> getAllProducts();

    ResponseEntity<ProductDTO> getProductById (@PathVariable ("productId") Long id);

    ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                         @RequestBody ProductPayload payload);

    ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id);

}
