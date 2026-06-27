package cz.hornakova.barbora.tennisclub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourtRequest(

        @NotBlank
        String name,

        @NotNull
        Long surfaceTypeId

) {
}