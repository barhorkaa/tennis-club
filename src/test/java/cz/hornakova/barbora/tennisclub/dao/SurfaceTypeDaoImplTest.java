package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.SurfaceTypeDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SurfaceTypeDaoImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private SurfaceTypeDaoImpl dao;

    @Test
    void getById_shouldReturnEmpty_whenDeleted() {
        SurfaceType st = new SurfaceType();
        st.setDeleted(true);

        when(em.find(SurfaceType.class, 1L)).thenReturn(st);

        assertTrue(dao.getById(1L).isEmpty());
    }

    @Test
    void save_shouldPersist() {
        SurfaceType st = new SurfaceType();

        dao.save(st);

        verify(em).persist(st);
    }

    @Test
    void update_shouldMerge() {
        SurfaceType st = new SurfaceType();

        dao.update(st);

        verify(em).merge(st);
    }

    @Test
    void delete_shouldSoftDelete() {
        SurfaceType st = new SurfaceType();

        dao.delete(st);

        assertTrue(st.isDeleted());
        verify(em).merge(st);
    }
}
