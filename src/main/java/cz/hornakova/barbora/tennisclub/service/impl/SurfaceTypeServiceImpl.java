package cz.hornakova.barbora.tennisclub.service.impl;

import cz.hornakova.barbora.tennisclub.dao.SurfaceTypeDao;
import cz.hornakova.barbora.tennisclub.exception.SurfaceTypeNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.SurfaceTypeMapper;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import cz.hornakova.barbora.tennisclub.service.SurfaceTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SurfaceTypeServiceImpl implements SurfaceTypeService {

    private final SurfaceTypeDao surfaceTypeDao;
    private final SurfaceTypeMapper mapper;

    public SurfaceTypeServiceImpl(SurfaceTypeDao surfaceTypeDao, SurfaceTypeMapper mapper) {
        this.surfaceTypeDao = surfaceTypeDao;
        this.mapper = mapper;
    }

    @Override
    public List<SurfaceTypeResponse> getAll() {
        return surfaceTypeDao.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public SurfaceTypeResponse getById(Long id) {
        SurfaceType surfaceType = surfaceTypeDao.getById(id)
                .orElseThrow(() ->
                        new SurfaceTypeNotFoundException(id));

        return mapper.toResponse(surfaceType);
    }

    @Override
    public SurfaceTypeResponse create(SurfaceTypeRequest request) {
        SurfaceType surfaceType = mapper.toEntity(request);

        surfaceTypeDao.save(surfaceType);

        return mapper.toResponse(surfaceType);
    }

    @Override
    public SurfaceTypeResponse update(Long id, SurfaceTypeRequest request) {
        SurfaceType surfaceType = surfaceTypeDao.getById(id)
                .orElseThrow(() ->
                        new SurfaceTypeNotFoundException(id));

        mapper.updateEntity(surfaceType, request);

        surfaceTypeDao.save(surfaceType);

        return mapper.toResponse(surfaceType);
    }

    @Override
    public void delete(Long id) {
        SurfaceType surfaceType = surfaceTypeDao.getById(id)
                .orElseThrow(() ->
                        new SurfaceTypeNotFoundException(id));

        surfaceTypeDao.delete(surfaceType);
    }
}
