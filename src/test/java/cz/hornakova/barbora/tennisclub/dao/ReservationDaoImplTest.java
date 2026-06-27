package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.dao.impl.ReservationDaoImpl;
import cz.hornakova.barbora.tennisclub.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ReservationDaoImpl.class)
class ReservationDaoImplTest {

    @Autowired
    ReservationDaoImpl dao;

    @PersistenceContext
    EntityManager em;

    @Test
    void getAll_shouldExcludeDeleted() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setCourt(court);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10,0));
        r.setEnd(LocalTime.of(11,0));
        r.setDeleted(false);
        r.setCustomer(customer);
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());

        Reservation d = new Reservation();
        d.setCourt(court);
        d.setDate(LocalDate.now());
        d.setStart(LocalTime.of(10,0));
        d.setEnd(LocalTime.of(11,0));
        d.setCustomer(customer);
        d.setGameType(GameType.SINGLES);
        d.setCreatedAt(LocalDateTime.now());
        d.setDeleted(true);

        em.persist(r);
        em.persist(d);

        assertEquals(1, dao.getAll().size());
    }

    @Test
    void getById_shouldReturnEmptyWhenDeleted() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setCourt(court);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10,0));
        r.setEnd(LocalTime.of(11,0));
        r.setDeleted(true);
        r.setCustomer(customer);
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());

        em.persist(r);

        assertTrue(dao.getById(r.getId()).isEmpty());
    }

    @Test
    void overlap_shouldDetectConflict() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setCourt(court);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10,0));
        r.setEnd(LocalTime.of(11,0));
        r.setDeleted(false);
        r.setCustomer(customer);
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());

        em.persist(r);

        boolean overlap = dao.isOverlapping(
                court.getId(),
                r.getDate(),
                LocalTime.of(10,30),
                LocalTime.of(11,30)
        );

        assertTrue(overlap);
    }

    @Test
    void overlap_shouldReturnFalse() {
        boolean result = dao.isOverlapping(
                999L,
                LocalDate.now(),
                LocalTime.of(1,0),
                LocalTime.of(2,0)
        );

        assertFalse(result);
    }

    @Test
    void updateOverlap_shouldIgnoreSameReservation() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation existing = new Reservation();
        existing.setCourt(court);
        existing.setDate(LocalDate.now());
        existing.setStart(LocalTime.of(10, 0));
        existing.setEnd(LocalTime.of(11, 0));
        existing.setDeleted(false);
        existing.setCustomer(customer);
        existing.setGameType(GameType.SINGLES);
        existing.setCreatedAt(LocalDateTime.now());

        em.persist(existing);
        em.flush();

        Long existingId = existing.getId();

        em.clear();

        boolean overlap = dao.isOverlapping(
                existingId,
                court.getId(),
                LocalDate.now(),
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        );

        assertFalse(overlap);
    }
}
