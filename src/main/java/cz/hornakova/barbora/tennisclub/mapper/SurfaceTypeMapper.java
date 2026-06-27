package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import org.springframework.stereotype.Component;

@Component
public class SurfaceTypeMapper {

    public SurfaceTypeResponse toResponse(SurfaceType surfaceType) {
        return new SurfaceTypeResponse(
                surfaceType.getId(),
                surfaceType.getName(),
                surfaceType.getPricePerMinute()
        );
    }

    public SurfaceType toEntity(SurfaceTypeRequest request) {
        SurfaceType surfaceType = new SurfaceType();

        surfaceType.setName(request.name());
        surfaceType.setPricePerMinute(request.pricePerMinute());

        return surfaceType;
    }

    public void updateEntity(
            SurfaceType surfaceType,
            SurfaceTypeRequest request
    ) {
        surfaceType.setName(request.name());
        surfaceType.setPricePerMinute(request.pricePerMinute());
    }
}
