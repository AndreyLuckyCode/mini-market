package andrey.code.rest.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductPayload(
        @NotNull
                @Size(min = 3, max = 50)
        String name,

        @NotNull
        Integer price,

        @NotNull
                @Size(min = 3, max = 100)
        String somePrivateInfo) {
}
