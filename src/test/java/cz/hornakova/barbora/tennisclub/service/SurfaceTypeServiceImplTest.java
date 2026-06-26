package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.dao.SurfaceTypeDao;
import cz.hornakova.barbora.tennisclub.exception.SurfaceTypeNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.SurfaceTypeMapper;
import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import cz.hornakova.barbora.tennisclub.service.impl.SurfaceTypeServiceImpl;
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
class SurfaceTypeServiceImplTest {

    @Mock
    private SurfaceTypeDao dao;

    @Mock
    private SurfaceTypeMapper mapper;

    @InjectMocks
    private SurfaceTypeServiceImpl service;

    @Test
    void getAll_shouldMap() {
        SurfaceType st = new SurfaceType();

        when(dao.getAll()).thenReturn(List.of(st));
        when(mapper.toResponse(st))
                .thenReturn(new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.35")));

        assertEquals(1, service.getAll().size());
    }

    @Test
    void getById_shouldThrow() {
        when(dao.getById(1L)).thenReturn(Optional.empty());

        assertThrows(SurfaceTypeNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void create_shouldSave() {
        SurfaceType st = new SurfaceType();
        SurfaceTypeCreateRequest req = new SurfaceTypeCreateRequest("Clay", new BigDecimal("0.35"));

        when(mapper.toEntity(req)).thenReturn(st);
        when(mapper.toResponse(st))
                .thenReturn(new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.35")));

        service.create(req);

        verify(dao).save(st);
    }

    @Test
    void update_shouldUpdate() {
        SurfaceType st = new SurfaceType();
        SurfaceTypeUpdateRequest req = new SurfaceTypeUpdateRequest("Hard", new BigDecimal("0.40"));

        when(dao.getById(1L)).thenReturn(Optional.of(st));

        service.update(1L, req);

        verify(dao).update(st);
    }

    @Test
    void delete_shouldSoftDelete() {
        SurfaceType st = new SurfaceType();

        when(dao.getById(1L)).thenReturn(Optional.of(st));

        service.delete(1L);

        verify(dao).delete(st);
    }
}
