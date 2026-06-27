package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.SurfaceTypeDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurfaceTypeDaoImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<SurfaceType> query;

    @InjectMocks
    private SurfaceTypeDaoImpl dao;

    // -------------------------
    // getAll()
    // -------------------------
    @Test
    void getAll_shouldReturnOnlyNonDeleted() {

        SurfaceType st = new SurfaceType();
        st.setDeleted(false);

        when(em.createQuery(anyString(), eq(SurfaceType.class)))
                .thenReturn(query);

        when(query.getResultList())
                .thenReturn(List.of(st));

        List<SurfaceType> result = dao.getAll();

        assertEquals(1, result.size());
        assertFalse(result.get(0).isDeleted());

        verify(em).createQuery(anyString(), eq(SurfaceType.class));
        verify(query).getResultList();
    }

    @Test
    void getAll_shouldReturnEmptyList() {

        when(em.createQuery(anyString(), eq(SurfaceType.class)))
                .thenReturn(query);

        when(query.getResultList())
                .thenReturn(List.of());

        List<SurfaceType> result = dao.getAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void getById_shouldReturnEmpty_whenDeleted() {

        SurfaceType st = new SurfaceType();
        st.setDeleted(true);

        when(em.find(SurfaceType.class, 1L)).thenReturn(st);

        assertTrue(dao.getById(1L).isEmpty());
    }

    @Test
    void getById_shouldReturnEmpty_whenNotFound() {

        when(em.find(SurfaceType.class, 1L)).thenReturn(null);

        assertTrue(dao.getById(1L).isEmpty());
    }

    @Test
    void getById_shouldReturnSurfaceType_whenValid() {

        SurfaceType st = new SurfaceType();
        st.setDeleted(false);

        when(em.find(SurfaceType.class, 1L)).thenReturn(st);

        Optional<SurfaceType> result = dao.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(st, result.get());
    }

    @Test
    void save_shouldPersist_whenNewEntity() {

        SurfaceType st = new SurfaceType();
        st.setId(0L);

        SurfaceType result = dao.save(st);

        verify(em).persist(st);
        assertSame(st, result);
    }

    @Test
    void save_shouldMerge_whenExistingEntity() {

        SurfaceType st = new SurfaceType();
        st.setId(1L);

        SurfaceType merged = new SurfaceType();

        when(em.merge(st)).thenReturn(merged);

        SurfaceType result = dao.save(st);

        verify(em).merge(st);
        assertSame(merged, result);
    }

    @Test
    void delete_shouldMarkAndMerge() {

        SurfaceType st = new SurfaceType();

        dao.delete(st);

        assertTrue(st.isDeleted());
        verify(em).merge(st);
    }
}