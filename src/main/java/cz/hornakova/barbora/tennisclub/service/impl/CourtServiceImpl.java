package cz.hornakova.barbora.tennisclub.service.impl;

import cz.hornakova.barbora.tennisclub.dao.CourtDao;
import cz.hornakova.barbora.tennisclub.dao.SurfaceTypeDao;
import cz.hornakova.barbora.tennisclub.exception.CourtNotFoundException;
import cz.hornakova.barbora.tennisclub.exception.SurfaceTypeNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.CourtMapper;
import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import cz.hornakova.barbora.tennisclub.service.CourtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourtServiceImpl implements CourtService {

    private final CourtDao courtDao;
    private final SurfaceTypeDao surfaceTypeDao;
    private final CourtMapper mapper;

    public CourtServiceImpl(CourtDao courtDao, SurfaceTypeDao surfaceTypeDao, CourtMapper mapper) {
        this.courtDao = courtDao;
        this.surfaceTypeDao = surfaceTypeDao;
        this.mapper = mapper;
    }

    @Override
    public List<CourtResponse> getAll() {
        return courtDao.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CourtResponse getById(Long id) {
        Court court = courtDao.getById(id)
                .orElseThrow(() -> new CourtNotFoundException(id));

        return mapper.toResponse(court);
    }

    @Override
    public CourtResponse create(CourtCreateRequest request) {

        SurfaceType surfaceType = surfaceTypeDao.getById(request.surfaceTypeId())
                .orElseThrow(() ->
                        new SurfaceTypeNotFoundException(request.surfaceTypeId()));

        Court court = mapper.toEntity(request, surfaceType);

        courtDao.save(court);

        return mapper.toResponse(court);
    }

    @Override
    public CourtResponse update(Long id, CourtUpdateRequest request) {

        Court court = courtDao.getById(id)
                .orElseThrow(() -> new CourtNotFoundException(id));

        SurfaceType surfaceType = surfaceTypeDao.getById(request.surfaceTypeId())
                .orElseThrow(() ->
                        new SurfaceTypeNotFoundException(request.surfaceTypeId()));

        mapper.updateEntity(court, request, surfaceType);

        Court saved = courtDao.save(court);

        return mapper.toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Court court = courtDao.getById(id)
                .orElseThrow(() -> new CourtNotFoundException(id));

        courtDao.delete(court);
    }
}
