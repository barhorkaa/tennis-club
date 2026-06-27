package cz.hornakova.barbora.tennisclub.dao.impl;

import cz.hornakova.barbora.tennisclub.dao.ReservationDao;
import cz.hornakova.barbora.tennisclub.model.entity.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Reservation> getAll() {
        return em.createQuery(
                        """
                           SELECT r
                           FROM Reservation r
                           WHERE r.deleted = false
                           """,
                        Reservation.class)
                .getResultList();
    }

    @Override
    public Optional<Reservation> getById(Long id) {
        Reservation reservation = em.find(Reservation.class, id);

        if (reservation == null || reservation.isDeleted()) {
            return Optional.empty();
        }

        return Optional.of(reservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == 0) {
            em.persist(reservation);
            return reservation;
        }
        return em.merge(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservation.setDeleted(true);

        em.merge(reservation);
    }

    @Override
    public List<Reservation> getByCourtId(long courtId) {
        return em.createQuery(
                        """
                           SELECT r
                           FROM Reservation r
                           WHERE r.court_id = :courtId
                           AND r.deleted = false
                           ORDER BY r.created_at
                           """,
                        Reservation.class)
                .setParameter("courtId", courtId)
                .getResultList();
    }

    @Override
    public List<Reservation> getByCustomerPhone(String customerPhone) {
        return em.createQuery(
                        """
                           SELECT r
                           FROM Reservation r
                           WHERE r.customer.phone_number = :customerPhone
                           AND r.deleted = false
                           """,
                        Reservation.class)
                .setParameter("customerPhone", customerPhone)
                .getResultList();
    }

    @Override
    public boolean isOverlapping(Long courtId, LocalDate date, LocalTime start, LocalTime end) {
        Long count = em.createQuery(
                        """
                        SELECT COUNT(r) FROM Reservation r
                        WHERE r.court.id = :courtId
                        AND r.date = :date
                        AND r.deleted = false
                        AND (r.start < :end AND r.end > :start)
                        """,
                        Long.class)
                .setParameter("courtId", courtId)
                .setParameter("date", date)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();

        return count > 0;
    }

    @Override
    public boolean isOverlapping(Long reservationId, Long courtId, LocalDate date, LocalTime start, LocalTime end) {

        Long count = em.createQuery("""
                        SELECT COUNT(r)
                        FROM Reservation r
                        WHERE (:reservationId IS NULL OR r.id <> :reservationId)
                        AND r.court.id = :courtId
                        AND r.date = :date
                        AND r.deleted = false
                        AND (r.start < :end AND r.end > :start)
                    """, Long.class)
                .setParameter("reservationId", reservationId)
                .setParameter("courtId", courtId)
                .setParameter("date", date)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();

        return count > 0;
    }

}
