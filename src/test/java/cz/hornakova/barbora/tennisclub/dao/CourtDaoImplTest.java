package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.CourtDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
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
class CourtDaoImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private CourtDaoImpl dao;

    @Test
    void getById_shouldReturnEmpty_whenNull() {
        when(em.find(Court.class, 1L)).thenReturn(null);

        assertTrue(dao.getById(1L).isEmpty());
    }

    @Test
    void getById_shouldReturnEmpty_whenDeleted() {
        Court c = new Court();
        c.setDeleted(true);

        when(em.find(Court.class, 1L)).thenReturn(c);

        assertTrue(dao.getById(1L).isEmpty());
    }

    @Test
    void save_shouldPersist() {
        Court c = new Court();

        dao.save(c);

    }

    @Test
    void update_shouldMerge() {
        Court c = new Court();
        c.setId(1L);

        dao.save(c);

        verify(em).merge(c);
    }

    @Test
    void delete_shouldSoftDelete() {
        Court c = new Court();

        dao.delete(c);

        assertTrue(c.isDeleted());
        verify(em).merge(c);
    }
}
