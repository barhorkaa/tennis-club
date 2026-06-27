package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.CourtRequest;
import cz.hornakova.barbora.tennisclub.model.dto.CourtResponse;
import cz.hornakova.barbora.tennisclub.model.dto.CourtRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import org.springframework.stereotype.Component;

@Component
public class CourtMapper {

    private final SurfaceTypeMapper surfaceTypeMapper;

    public CourtMapper(SurfaceTypeMapper surfaceTypeMapper) {
        this.surfaceTypeMapper = surfaceTypeMapper;
    }

    public CourtResponse toResponse(Court court) {
        return new CourtResponse(
                court.getId(),
                court.getName(),
                surfaceTypeMapper.toResponse(court.getSurfaceType())
        );
    }

    public Court toEntity(CourtRequest request, SurfaceType surfaceType) {
        Court court = new Court();

        court.setName(request.name());
        court.setSurfaceType(surfaceType);

        return court;
    }

    public void updateEntity(
            Court court,
            CourtRequest request,
            SurfaceType surfaceType
    ) {
        court.setName(request.name());
        court.setSurfaceType(surfaceType);
    }
}
