package cz.hornakova.barbora.tennisclub.model.dto;

public record CustomerCreateRequest(
        String name,
        String phoneNumber
) {
}
