package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.model.dto.ReservationCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationResponse;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationUpdateRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Reservation;

import java.util.List;

public interface ReservationService
        extends FullService<ReservationCreateRequest, ReservationUpdateRequest, ReservationResponse> {

    public List<ReservationResponse> getByCourtId(long courtId);

    public List<ReservationResponse> getByCustomerPhone(String customerPhone);

    public List<ReservationResponse> getByCustomerPhone(String customerPhone, boolean onlyFuture);
}
