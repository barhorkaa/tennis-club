package cz.hornakova.barbora.tennisclub.model.dto;

public record CourtUpdateRequest(

        String name,
        Long surfaceTypeId

) {
}
