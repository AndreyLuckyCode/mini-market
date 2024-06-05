package andrey.code.service;

import andrey.code.controller.payload.ProductPayload;
import andrey.code.entity.ProductEntity;
import andrey.code.mapper.ProductDTO;
import andrey.code.mapper.ProductMapper;
import andrey.code.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable ("productId") Long id) {

        Optional<ProductEntity> product = productRepository.findById(id);

        return product.map(productEntity -> ResponseEntity.ok(productMapper.toProductDTO(productEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateProduct(@PathVariable ("productId") Long id,
                                                @RequestBody ProductPayload payload) {

        this.productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    product.setName(payload.name());
                    product.setPrice(payload.price());
                    product.setSomePrivateInfo(payload.somePrivateInfo());
                }, () -> {
                    throw new NoSuchElementException();
                });
        return ResponseEntity.ok("Product info was updated");
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteProduct(@PathVariable ("productId") Long id) {

        productRepository.getReferenceById(id);
        productRepository.deleteById(id);

        return ResponseEntity.ok("Product was deleted");
    }
}
