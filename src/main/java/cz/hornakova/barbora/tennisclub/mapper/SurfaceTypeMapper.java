package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeUpdateRequest;
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

    public SurfaceType toEntity(SurfaceTypeCreateRequest request) {
        SurfaceType surfaceType = new SurfaceType();

        surfaceType.setName(request.name());
        surfaceType.setPricePerMinute(request.pricePerMinute());

        return surfaceType;
    }

    public void updateEntity(
            SurfaceType surfaceType,
            SurfaceTypeUpdateRequest request
    ) {
        surfaceType.setName(request.name());
        surfaceType.setPricePerMinute(request.pricePerMinute());
    }
}
