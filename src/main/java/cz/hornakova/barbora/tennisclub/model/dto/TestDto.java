package cz.hornakova.barbora.tennisclub.model.dto;

import jakarta.validation.constraints.NotBlank;

public class TestDto {
    @NotBlank
    public String name;
}