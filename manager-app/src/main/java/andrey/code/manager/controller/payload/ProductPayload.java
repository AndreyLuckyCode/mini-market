package andrey.code.manager.controller.payload;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductPayload(
        @NotNull(message = "{products.create.errors.name_is_null}")
        @Size(min = 3, max = 50, message = "{products.create.errors.name_size_is_invalid}")
        String name,

        @NotNull(message = "{products.create.errors.price_is_null}")
        Integer price,

        @NotNull
        @Size(min = 3, max = 100, message = "{products.create.errors.private_info_size_is_invalid}")
        String somePrivateInfo) {
}
