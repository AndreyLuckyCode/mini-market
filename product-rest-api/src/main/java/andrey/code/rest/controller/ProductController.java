package andrey.code.rest.controller;

import andrey.code.rest.controller.payload.ProductPayload;
import andrey.code.rest.mapper.ProductDTO;
import andrey.code.rest.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/v1")
public class ProductController {

    MessageSource messageSource;
    ProductService productService;

    private static final String CREATE_PRODUCT = "/products";
    private static final String GET_PRODUCT = "/products/{productId}";
    private static final String GET_PRODUCTS_LIST = "/products";
    private static final String UPDATE_PRODUCT = "/products/{productId}";
    private static final String DELETE_PRODUCT = "/products/{productId}";
    private static final String NOT_FOUND_ERROR = "errors.404.not_found";

    @PostMapping(CREATE_PRODUCT)
    @Operation(
            security = @SecurityRequirement(name = "keycloak"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    type = "object",
                                    properties = {
                                            @StringToClassMapItem(key = "name", value = String.class),
                                            @StringToClassMapItem(key = "price", value = Integer.class),
                                            @StringToClassMapItem(key = "somePrivateInfo", value = String.class)
                                    }
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            headers = @Header(name = "Content-Type", description = "Тип данных"),
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(
                                                    type = "object",
                                                    properties = {
                                                            @StringToClassMapItem(key = "name", value = String.class),
                                                            @StringToClassMapItem(key = "price", value = Integer.class),
                                                    }
                                            )
                                    )
                            }
                    )
            })
    public ResponseEntity<String> createProduct(@RequestBody ProductPayload payload){
        return productService.createProduct(payload);
    }

    @GetMapping(GET_PRODUCT)
    @Operation(
            security = @SecurityRequirement(name = "keycloak"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный запрос",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ProductDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Продукт не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            properties = {
                                                    @StringToClassMapItem(key = "message", value = String.class)
                                            }
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long id) {

        ProductDTO product = productService.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ERROR));

        return ResponseEntity.ok(product);
    }

    @GetMapping(GET_PRODUCTS_LIST)
    @Operation(
            security = @SecurityRequirement(name = "keycloak"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешный запрос",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "array"
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<List<ProductDTO>> getAllProducts(){

        return productService.getAllProducts();
    }

    @PatchMapping(UPDATE_PRODUCT)
    @Operation(
            security = @SecurityRequirement(name = "keycloak"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    type = "object",
                                    properties = {
                                            @StringToClassMapItem(key = "name", value = String.class),
                                            @StringToClassMapItem(key = "price", value = String.class),
                                            @StringToClassMapItem(key = "somePrivateInfo", value = String.class)
                                    }
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно обновлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            properties = {
                                                    @StringToClassMapItem(key = "message", value = String.class)
                                            }
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Продукт не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            properties = {
                                                    @StringToClassMapItem(key = "message", value = String.class)
                                            }
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long id,
                                                @RequestBody ProductPayload payload) {

        productService.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ERROR));

        return productService.updateProduct(id, payload);
    }

    @DeleteMapping(DELETE_PRODUCT)
    @Operation(
            security = @SecurityRequirement(name = "keycloak"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Продукт успешно удален",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            properties = {
                                                    @StringToClassMapItem(key = "message", value = String.class)
                                            }
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Продукт не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            type = "object",
                                            properties = {
                                                    @StringToClassMapItem(key = "message", value = String.class)
                                            }
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long id) {

        productService.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ERROR));

        return productService.deleteProduct(id);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        this.messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
