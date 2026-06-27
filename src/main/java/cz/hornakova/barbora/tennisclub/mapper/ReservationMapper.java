package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.ReservationCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationResponse;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationUpdateRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import cz.hornakova.barbora.tennisclub.model.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    private final CourtMapper courtMapper;
    private final CustomerMapper customerMapper;

    public ReservationMapper(
            CourtMapper courtMapper,
            CustomerMapper customerMapper
    ) {
        this.courtMapper = courtMapper;
        this.customerMapper = customerMapper;
    }

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                courtMapper.toResponse(reservation.getCourt()),
                customerMapper.toResponse(reservation.getCustomer()),
                reservation.getGameType(),
                reservation.getDate(),
                reservation.getStart(),
                reservation.getEnd(),
                reservation.getPrice(),
                reservation.getCreatedAt(),
                reservation.isDeleted()
        );
    }

    public Reservation toEntity(
            ReservationCreateRequest request,
            Court court,
            Customer customer
    ) {
        Reservation reservation = new Reservation();

        reservation.setCourt(court);
        reservation.setCustomer(customer);
        reservation.setGameType(request.gameType());
        reservation.setDate(request.date());
        reservation.setStart(request.start());
        reservation.setEnd(request.end());
        reservation.setDeleted(false);

        return reservation;
    }

    public void updateEntity(
            Reservation reservation,
            ReservationUpdateRequest request,
            Court court,
            Customer customer
    ) {
        reservation.setCourt(court);
        reservation.setCustomer(customer);
        reservation.setGameType(request.gameType());
        reservation.setDate(request.date());
        reservation.setStart(request.start());
        reservation.setEnd(request.end());
        reservation.setDeleted(request.deleted());
    }
}
