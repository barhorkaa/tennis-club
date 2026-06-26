package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.dao.CourtDao;
import cz.hornakova.barbora.tennisclub.dao.SurfaceTypeDao;
import cz.hornakova.barbora.tennisclub.exception.CourtNotFoundException;
import cz.hornakova.barbora.tennisclub.exception.SurfaceTypeNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.CourtMapper;
import cz.hornakova.barbora.tennisclub.model.dto.CourtCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.CourtResponse;
import cz.hornakova.barbora.tennisclub.model.dto.CourtUpdateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import cz.hornakova.barbora.tennisclub.service.impl.CourtServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtServiceImplTest {

    @Mock
    private CourtDao courtDao;

    @Mock
    private SurfaceTypeDao surfaceTypeDao;

    @Mock
    private CourtMapper mapper;

    @InjectMocks
    private CourtServiceImpl service;

    @Test
    void getAll_shouldMapAll() {
        Court c = new Court();

        when(courtDao.getAll()).thenReturn(List.of(c));
        when(mapper.toResponse(c))
                .thenReturn(new CourtResponse(1L, "A", new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40"))));

        var result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getById_shouldReturnCourt() {
        Court c = new Court();

        when(courtDao.getById(1L)).thenReturn(Optional.of(c));
        when(mapper.toResponse(c))
                .thenReturn(new CourtResponse(1L, "A", new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40"))));

        var result = service.getById(1L);

        assertEquals("A", result.name());
    }

    @Test
    void getById_shouldThrow() {
        when(courtDao.getById(1L)).thenReturn(Optional.empty());

        assertThrows(CourtNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void create_shouldSaveCourt() {
        CourtCreateRequest req = new CourtCreateRequest("A", 1L);

        SurfaceType st = new SurfaceType();
        Court c = new Court();

        when(surfaceTypeDao.getById(1L)).thenReturn(Optional.of(st));
        when(mapper.toEntity(req, st)).thenReturn(c);
        when(mapper.toResponse(c))
                .thenReturn(new CourtResponse(1L, "A", new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40"))));

        var result = service.create(req);

        verify(courtDao).save(c);
        assertEquals("A", result.name());
    }

    @Test
    void create_shouldThrowSurfaceTypeNotFound() {
        CourtCreateRequest req = new CourtCreateRequest("A", 1L);

        when(surfaceTypeDao.getById(1L)).thenReturn(Optional.empty());

        assertThrows(SurfaceTypeNotFoundException.class,
                () -> service.create(req));
    }

    @Test
    void update_shouldUpdateCourt() {
        CourtUpdateRequest req = new CourtUpdateRequest("B", 2L);

        Court c = new Court();
        SurfaceType st = new SurfaceType();

        when(courtDao.getById(1L)).thenReturn(Optional.of(c));
        when(surfaceTypeDao.getById(2L)).thenReturn(Optional.of(st));

        doNothing().when(mapper).updateEntity(c, req, st);

        when(mapper.toResponse(c))
                .thenReturn(new CourtResponse(1L, "B", new SurfaceTypeResponse(2L, "Clay", new BigDecimal("0.40"))));

        var result = service.update(1L, req);

        verify(courtDao).update(c);
        assertEquals("B", result.name());
    }

    @Test
    void delete_shouldSoftDelete() {
        Court c = new Court();

        when(courtDao.getById(1L)).thenReturn(Optional.of(c));

        service.delete(1L);

        verify(courtDao).delete(c);
    }
}
