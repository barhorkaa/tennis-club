package cz.hornakova.barbora.tennisclub.model.dto;

public record CustomerResponse(
        Long id,
        String name,
        String phoneNumber
) {
}
