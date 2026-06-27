package cz.hornakova.barbora.tennisclub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SurfaceTypeRequest(

        @NotBlank
        String name,

        @NotNull
        @Positive(message = "Price must be greater than zero")
        BigDecimal pricePerMinute
) {
}
