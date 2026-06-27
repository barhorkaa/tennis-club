package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.CourtDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CourtDaoImpl.class)
class CourtDaoImplTest {

    @Autowired CourtDaoImpl dao;

    @PersistenceContext EntityManager em;

    @Test
    void getAll_shouldReturnNonDeletedOnly() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court c1 = new Court(st, "Test Court 1");
        c1.setDeleted(false);

        Court c2 = new Court(st, "Deleted Test Court 2");
        c2.setDeleted(true);

        em.persist(c1);
        em.persist(c2);

        List<Court> result = dao.getAll();

        assertTrue(result.stream()
                .anyMatch(c -> c.getName().equals("Test Court 1")));
        assertFalse(result.stream()
                .anyMatch(c -> c.getName().equals("Deleted Test Court 2")));
    }

    @Test
    void getById_shouldReturnCourt() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        court.setDeleted(false);

        em.persist(court);

        Optional<Court> result = dao.getById(court.getId());

        assertTrue(result.isPresent());
        assertEquals("Court 1", result.get().getName());
    }

    @Test
    void getById_shouldReturnEmpty_whenDeleted() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        court.setDeleted(true);

        em.persist(court);

        Optional<Court> result = dao.getById(court.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void getById_shouldReturnEmpty_whenNotFound() {
        Optional<Court> result = dao.getById(999L);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_shouldPersistNewEntity() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "New Court");
        court.setDeleted(false);

        Court saved = dao.save(court);

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    void save_shouldMergeExistingEntity() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Old Name");
        court.setDeleted(false);

        em.persist(court);
        em.flush();

        court.setName("Updated Name");

        Court updated = dao.save(court);

        assertEquals("Updated Name", updated.getName());
    }

    @Test
    void delete_shouldMarkAsDeleted() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court");
        court.setDeleted(false);

        em.persist(court);
        em.flush();

        dao.delete(court);

        Court deleted = em.find(Court.class, court.getId());

        assertTrue(deleted.isDeleted());
    }
}