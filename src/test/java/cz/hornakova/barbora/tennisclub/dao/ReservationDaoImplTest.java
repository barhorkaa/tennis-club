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
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ReservationDaoImpl.class)
class ReservationDaoImplTest {

    @Autowired ReservationDaoImpl dao;

    @PersistenceContext EntityManager em;

    @Test
    void getAll_shouldExcludeDeleted() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation active = new Reservation();
        active.setCourt(court);
        active.setDate(LocalDate.now());
        active.setStart(LocalTime.of(10,0));
        active.setEnd(LocalTime.of(11,0));
        active.setDeleted(false);
        active.setCustomer(customer);
        active.setGameType(GameType.SINGLES);
        active.setCreatedAt(LocalDateTime.now());

        Reservation deleted = new Reservation();
        deleted.setCourt(court);
        deleted.setDate(LocalDate.now());
        deleted.setStart(LocalTime.of(10,0));
        deleted.setEnd(LocalTime.of(11,0));
        deleted.setDeleted(true);
        deleted.setCustomer(customer);
        deleted.setGameType(GameType.SINGLES);
        deleted.setCreatedAt(LocalDateTime.now());

        em.persist(active);
        em.persist(deleted);

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
    void getById_shouldReturnValueWhenValid() {

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

        assertTrue(dao.getById(r.getId()).isPresent());
    }

    @Test
    void save_shouldPersistNewEntity() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setCourt(court);
        r.setCustomer(customer);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10, 0));
        r.setEnd(LocalTime.of(11, 0));
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());
        r.setDeleted(false);

        dao.save(r);

        assertNotNull(r.getId());
    }

    @Test
    void save_shouldMergeExistingEntity() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setCourt(court);
        r.setCustomer(customer);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10,0));
        r.setEnd(LocalTime.of(11,0));
        r.setDeleted(false);
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());

        em.persist(r);
        em.flush();

        dao.save(r);

        assertNotNull(r.getId());
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

        assertTrue(dao.isOverlapping(
                court.getId(),
                r.getDate(),
                LocalTime.of(10,30),
                LocalTime.of(11,30)
        ));
    }

    @Test
    void overlap_shouldReturnFalse_whenNoConflict() {

        assertFalse(dao.isOverlapping(
                999L,
                LocalDate.now(),
                LocalTime.of(1,0),
                LocalTime.of(2,0)
        ));
    }

    @Test
    void overlap_shouldNotDetectBoundaryAsConflict() {

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

        assertFalse(dao.isOverlapping(
                court.getId(),
                r.getDate(),
                LocalTime.of(11,0),
                LocalTime.of(12,0)
        ));
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

        Long id = existing.getId();
        em.clear();

        assertFalse(dao.isOverlapping(
                id,
                court.getId(),
                LocalDate.now(),
                LocalTime.of(10, 30),
                LocalTime.of(11, 30)
        ));
    }

    @Test
    void delete_shouldPersistSoftDelete() {

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
        em.flush();

        dao.delete(r);
        em.flush();
        em.clear();

        Reservation fromDb = em.find(Reservation.class, r.getId());

        assertTrue(fromDb.isDeleted());
    }

    @Test
    void save_shouldPersist_whenIdIsZero() {

        SurfaceType st = new SurfaceType("Clay", BigDecimal.ONE);
        em.persist(st);

        Court court = new Court(st, "Court 1");
        em.persist(court);

        Customer customer = new Customer("name", "147852");
        em.persist(customer);

        Reservation r = new Reservation();
        r.setId(0L);
        r.setCourt(court);
        r.setCustomer(customer);
        r.setDate(LocalDate.now());
        r.setStart(LocalTime.of(10,0));
        r.setEnd(LocalTime.of(11,0));
        r.setGameType(GameType.SINGLES);
        r.setCreatedAt(LocalDateTime.now());

        dao.save(r);

        assertNotNull(r.getId());
    }

    @Test
    void overlap_shouldIgnoreDeletedReservations() {

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
        em.flush();

        assertFalse(dao.isOverlapping(
                court.getId(),
                r.getDate(),
                LocalTime.of(10,30),
                LocalTime.of(11,30)
        ));
    }
}