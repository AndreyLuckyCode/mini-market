package andrey.code.manager.controller;

import andrey.code.manager.client.RestClientProductRestClient;
import andrey.code.manager.controller.payload.ProductPayload;
import andrey.code.manager.entity.ProductDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;


@ExtendWith(MockitoExtension.class)
@DisplayName("Module test ProductController")
class ProductControllerTest {

    @Mock
    RestClientProductRestClient restClient;
    @InjectMocks
    ProductController controller;

    @Test
    @DisplayName("createProduct creates product and return Response")
    void createProduct_RequestIsValid_ReturnResponse() throws BindException {

        // given
        var payload = new ProductPayload("New product", 10, "some hidden info");
        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "payload");

        ResponseEntity<String> expectedResponse = new ResponseEntity<>("Product created successfully", CREATED);
        doReturn(expectedResponse).when(restClient).createProduct(payload);

        // when
        ResponseEntity<String> actualResponse = controller.createProduct(payload, bindingResult);

        // then
        assertEquals(expectedResponse, actualResponse);
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());

        verify(restClient).createProduct(payload);
        verifyNoMoreInteractions(restClient);
    }

    @Test
    @DisplayName("createProduct throws BindException")
    void createProduct_RequestIsInvalid_ThrowsBindException() {

        // given
        var payload = new ProductPayload(" ", 1, null);
        BindingResult bindingResult = mock(BindingResult.class);

        // when
        when(bindingResult.hasErrors()).thenReturn(true);

        // then
        assertThrows(BindException.class, () -> controller.createProduct(payload, bindingResult));
    }

    @Test
    @DisplayName("getProductById returns product by id")
    void getProductById_ProductExists_ReturnsProduct() {

        // given
        Long productId = 1L;
        ProductDTO product = new ProductDTO("Existing Product", 10);
        ResponseEntity<Optional<ProductDTO>> responseEntity =
                ResponseEntity.of(Optional.of(Optional.of(product)));

        when(restClient.getProductById(productId)).thenReturn(responseEntity);

        // when
        ResponseEntity<ProductDTO> actualResponse = controller.getProductById(productId);

        // then
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(product, actualResponse.getBody());

        verify(restClient).getProductById(productId);
        verifyNoMoreInteractions(restClient);
    }

    @Test
    @DisplayName("getProductById returns NOT_FOUND when product does not exist")
    void getProductById_ProductDoesNotExist_ReturnsNotFound() {

        // given
        Long productId = 1L;
        ResponseEntity<Optional<ProductDTO>> expectedResponse =
                new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);

        // when
        when(restClient.getProductById(productId)).thenReturn(expectedResponse);

        // then
        ResponseEntity<ProductDTO> response = controller.getProductById(productId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(restClient).getProductById(productId);
        verifyNoMoreInteractions(restClient);
    }

    @Test
    @DisplayName("getAllProducts returns list of products")
    void getAllProducts_ReturnsListOfProducts() {

        // given
        List<ProductDTO> products = Arrays.asList(
                new ProductDTO("Product1", 10),
                new ProductDTO("Product2", 20)
        );
        ResponseEntity<List<ProductDTO>> responseEntity = ResponseEntity.ok(products);

        when(restClient.getAllProducts()).thenReturn(responseEntity);

        // when
        ResponseEntity<List<ProductDTO>> actualResponse = controller.getAllProducts();

        // then
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(products, actualResponse.getBody());

        verify(restClient).getAllProducts();
    }
}