package andrey.code.rest.service;

import andrey.code.rest.controller.payload.ProductPayload;
import andrey.code.rest.entity.ProductEntity;
import andrey.code.rest.mapper.ProductMapper;
import andrey.code.rest.repository.ProductRepository;
import andrey.code.rest.mapper.ProductDTO;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService{

    ProductMapper productMapper;
    ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload) {
        productRepository.saveAndFlush(
                new ProductEntity(null, payload.name(), payload.price(), payload.somePrivateInfo()));
        return ResponseEntity.ok("New product was created");
    }

    @Override
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductDTO> productDTOList = productMapper.toProductDTOList(productEntityList);
        return ResponseEntity.ok(productDTOList);
    }

    @Override
    public Optional<ProductDTO> getProductById(@PathVariable ("productId") Long id) {
        return productRepository.findById(id).map(productMapper::toProductDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                                @RequestBody ProductPayload payload) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductEntity productEntity = product.get();
            productEntity.setName(payload.name());
            productEntity.setPrice(payload.price());
            productEntity.setSomePrivateInfo(payload.somePrivateInfo());
            return ResponseEntity.ok("Product info was updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product was deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
}
