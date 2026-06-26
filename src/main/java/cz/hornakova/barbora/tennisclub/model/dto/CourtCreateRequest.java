package cz.hornakova.barbora.tennisclub.model.dto;

public record CourtCreateRequest(

        String name,
        Long surfaceTypeId

) {
}