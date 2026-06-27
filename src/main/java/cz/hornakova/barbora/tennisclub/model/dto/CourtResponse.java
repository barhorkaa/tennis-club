package cz.hornakova.barbora.tennisclub.model.dto;

public record CourtResponse(

        Long id,
        String name,
        SurfaceTypeResponse surfaceType

) {
}
