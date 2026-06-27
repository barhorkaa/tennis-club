package cz.hornakova.barbora.tennisclub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateRequest(

        @NotBlank
        String name,

        @NotNull
        String phoneNumber
) {
}
