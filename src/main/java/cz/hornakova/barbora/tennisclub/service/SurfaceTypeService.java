package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeUpdateRequest;

public interface SurfaceTypeService
        extends FullService<SurfaceTypeCreateRequest, SurfaceTypeUpdateRequest, SurfaceTypeResponse> {
}
