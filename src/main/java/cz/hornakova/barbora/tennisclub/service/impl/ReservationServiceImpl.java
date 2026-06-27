package cz.hornakova.barbora.tennisclub.service.impl;

import cz.hornakova.barbora.tennisclub.dao.CourtDao;
import cz.hornakova.barbora.tennisclub.dao.CustomerDao;
import cz.hornakova.barbora.tennisclub.dao.ReservationDao;
import cz.hornakova.barbora.tennisclub.exception.CourtNotFoundException;
import cz.hornakova.barbora.tennisclub.exception.CustomerNotFoundException;
import cz.hornakova.barbora.tennisclub.exception.ReservationCollisionException;
import cz.hornakova.barbora.tennisclub.exception.ReservationNotFoundException;
import cz.hornakova.barbora.tennisclub.mapper.ReservationMapper;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationResponse;
import cz.hornakova.barbora.tennisclub.model.dto.ReservationUpdateRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.Customer;
import cz.hornakova.barbora.tennisclub.model.entity.GameType;
import cz.hornakova.barbora.tennisclub.model.entity.Reservation;
import cz.hornakova.barbora.tennisclub.service.ReservationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final CustomerDao customerDao;
    private final CourtDao courtDao;
    private final ReservationMapper mapper;

    public ReservationServiceImpl(ReservationMapper mapper, CourtDao courtDao, CustomerDao customerDao, ReservationDao reservationDao) {
        this.mapper = mapper;
        this.courtDao = courtDao;
        this.customerDao = customerDao;
        this.reservationDao = reservationDao;
    }

    @Override
    public List<ReservationResponse> getByCourtId(long courtId) {

        return reservationDao.getByCourtId(courtId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getByCustomerPhone(String customerPhone) {

        return reservationDao.getByCustomerPhone(customerPhone)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getByCustomerPhone(String customerPhone, boolean onlyFuture) {

        List<Reservation> reservations = reservationDao.getByCustomerPhone(customerPhone);

        if (!onlyFuture) {
            return reservations
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        LocalDateTime now = LocalDateTime.now();

        return reservations.stream()
                .filter(r ->
                        LocalDateTime.of(r.getDate(), r.getStart()).isAfter(now)
                )
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> getAll() {

        return reservationDao.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse getById(Long id) {

        Reservation reservation = reservationDao.getById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        return mapper.toResponse(reservation);
    }

    private BigDecimal calculatePrice(
            Court court,
            GameType gameType,
            LocalTime start,
            LocalTime end
    ) {

        long minutes = Duration.between(start, end).toMinutes();

        BigDecimal multiplier =
                gameType == GameType.DOUBLES
                        ? BigDecimal.valueOf(1.5)
                        : BigDecimal.ONE;

        return court.getSurfaceType()
                .getPricePerMinute()
                .multiply(BigDecimal.valueOf(minutes))
                .multiply(multiplier);
    }

    @Override
    public ReservationResponse create(ReservationCreateRequest request) {

        Court court = courtDao.getById(request.courtId())
                .orElseThrow(() -> new CourtNotFoundException(request.courtId()));

        Customer customer;
        Optional<Customer> possibleCustomer = customerDao.getByPhoneNumber(request.customerPhone());

        customer = possibleCustomer.orElseGet(() ->
                customerDao.save(new Customer(
                        request.customerName(),
                        request.customerPhone()
                ))
        );

        if (reservationDao.isOverlapping(
                court.getId(),
                request.date(),
                request.start(),
                request.end())) {
            throw new ReservationCollisionException();
        }

        Reservation reservation = mapper.toEntity(request, court, customer);

        reservation.setPrice(
                calculatePrice(
                        court,
                        request.gameType(),
                        request.start(),
                        request.end()
                )
        );

        reservationDao.save(reservation);

        return mapper.toResponse(reservation);
    }

    @Override
    public ReservationResponse update(Long id, ReservationUpdateRequest request) {

        Reservation reservation = reservationDao.getById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        Court court = courtDao.getById(request.courtId())
                .orElseThrow(() -> new CourtNotFoundException(request.courtId()));

        Customer customer = customerDao.getById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        if (reservationDao.isOverlapping(
                id,
                court.getId(),
                request.date(),
                request.start(),
                request.end())) {
            throw new ReservationCollisionException();
        }

        mapper.updateEntity(
                reservation,
                request,
                court,
                customer
        );

        reservation.setPrice(
                calculatePrice(
                        court,
                        request.gameType(),
                        request.start(),
                        request.end()
                )
        );

        reservationDao.save(reservation);

        return mapper.toResponse(reservation);
    }

    @Override
    public void delete(Long id) {
        Reservation reservation = reservationDao.getById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));

        reservationDao.delete(reservation);
    }
}
