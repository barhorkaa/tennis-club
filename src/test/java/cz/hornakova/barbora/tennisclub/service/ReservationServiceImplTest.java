package cz.hornakova.barbora.tennisclub.service;

import cz.hornakova.barbora.tennisclub.dao.CourtDao;
import cz.hornakova.barbora.tennisclub.dao.CustomerDao;
import cz.hornakova.barbora.tennisclub.dao.ReservationDao;
import cz.hornakova.barbora.tennisclub.exception.ReservationCollisionException;
import cz.hornakova.barbora.tennisclub.mapper.ReservationMapper;
import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.*;
import cz.hornakova.barbora.tennisclub.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    ReservationDao reservationDao;

    @Mock
    CustomerDao customerDao;

    @Mock
    CourtDao courtDao;

    @Mock
    ReservationMapper mapper;

    @InjectMocks
    ReservationServiceImpl service;

    @Test
    void create_shouldSetPriceAndPersistEntity() {

        ReservationCreateRequest req = new ReservationCreateRequest(
                1L,
                "John",
                "123",
                GameType.SINGLES,
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        Court court = mock(Court.class);
        SurfaceType surface = mock(SurfaceType.class);

        when(court.getId()).thenReturn(1L);
        when(court.getSurfaceType()).thenReturn(surface);
        when(surface.getPricePerMinute()).thenReturn(BigDecimal.valueOf(2));

        Customer customer = new Customer("John", "123");

        when(courtDao.getById(1L)).thenReturn(Optional.of(court));
        when(customerDao.getByPhoneNumber("123")).thenReturn(Optional.of(customer));

        when(reservationDao.isOverlapping(anyLong(), any(), any(), any()))
                .thenReturn(false);

        when(mapper.toEntity(any(), eq(court), eq(customer)))
                .thenAnswer(inv -> {
                    Reservation r = new Reservation();
                    r.setCourt(court);
                    r.setCustomer(customer);
                    r.setDate(req.date());
                    r.setStart(req.start());
                    r.setEnd(req.end());
                    r.setGameType(req.gameType());
                    return r;
                });

        when(mapper.toResponse(any()))
                .thenReturn(mock(ReservationResponse.class));

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);

        service.create(req);

        verify(reservationDao).save(captor.capture());

        Reservation saved = captor.getValue();

        assertEquals(court, saved.getCourt());
        assertEquals(customer, saved.getCustomer());
        assertEquals(GameType.SINGLES, saved.getGameType());

        assertEquals(BigDecimal.valueOf(120), saved.getPrice());
    }

    @Test
    void getByCustomerPhone_shouldFilterFutureOnly() {

        Reservation past = mock(Reservation.class);
        Reservation future = mock(Reservation.class);

        when(past.getDate()).thenReturn(LocalDate.now().minusDays(1));
        when(past.getStart()).thenReturn(LocalTime.NOON);

        when(future.getDate()).thenReturn(LocalDate.now().plusDays(1));
        when(future.getStart()).thenReturn(LocalTime.NOON);

        when(reservationDao.getByCustomerPhone("123"))
                .thenReturn(List.of(past, future));

        when(mapper.toResponse(any()))
                .thenReturn(mock(ReservationResponse.class));

        List<ReservationResponse> result =
                service.getByCustomerPhone("123", true);

        assertEquals(1, result.size());
    }

    @Test
    void update_shouldRecalculatePrice() {

        Reservation existing = new Reservation();

        ReservationUpdateRequest req = new ReservationUpdateRequest(
                1L,
                2L,
                GameType.DOUBLES,
                LocalDate.now(),
                LocalTime.of(10,0),
                LocalTime.of(12,0),
                false
        );

        Court court = mock(Court.class);
        SurfaceType surface = mock(SurfaceType.class);

        Customer customer = new Customer();

        when(reservationDao.getById(1L))
                .thenReturn(Optional.of(existing));

        when(courtDao.getById(1L))
                .thenReturn(Optional.of(court));

        when(customerDao.getById(2L))
                .thenReturn(Optional.of(customer));

        when(court.getSurfaceType()).thenReturn(surface);
        when(surface.getPricePerMinute()).thenReturn(BigDecimal.valueOf(1));

        when(reservationDao.isOverlapping(anyLong(), anyLong(), any(), any(), any()))
                .thenReturn(false);

        when(mapper.toResponse(any()))
                .thenReturn(mock(ReservationResponse.class));

        service.update(1L, req);

        assertEquals(0, existing.getPrice().compareTo(BigDecimal.valueOf(180)));
    }

    @Test
    void create_shouldThrowWhenOverlapping() {

        ReservationCreateRequest req = new ReservationCreateRequest(
                1L, "A", "123",
                GameType.SINGLES,
                LocalDate.now(),
                LocalTime.of(10,0),
                LocalTime.of(11,0)
        );

        when(courtDao.getById(anyLong()))
                .thenReturn(Optional.of(mock(Court.class)));

        when(customerDao.getByPhoneNumber(any()))
                .thenReturn(Optional.of(new Customer()));

        when(reservationDao.isOverlapping(anyLong(), any(), any(), any()))
                .thenReturn(true);

        assertThrows(ReservationCollisionException.class,
                () -> service.create(req));
    }

    @Test
    void delete_shouldMarkEntity() {

        Reservation r = new Reservation();

        when(reservationDao.getById(1L))
                .thenReturn(Optional.of(r));

        service.delete(1L);

        verify(reservationDao).delete(r);
    }
}
