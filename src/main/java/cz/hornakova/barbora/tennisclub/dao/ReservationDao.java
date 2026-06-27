package cz.hornakova.barbora.tennisclub.dao;

import cz.hornakova.barbora.tennisclub.model.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationDao extends Dao<Reservation> {

    public List<Reservation> getByCourtId(long courtId);

    public List<Reservation> getByCustomerPhone(String customerPhone);

    public boolean isOverlapping(Long courtId, LocalDate date, LocalTime start, LocalTime end);

    public boolean isOverlapping(Long reservationId, Long courtId, LocalDate date, LocalTime start, LocalTime end);

}
