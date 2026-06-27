package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReservationMapperTest {

    @Mock
    private CourtMapper courtMapper;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toResponse_mapsCorrectly() {
        Court court = new Court();
        Customer customer = new Customer();

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCourt(court);
        reservation.setCustomer(customer);
        reservation.setGameType(GameType.SINGLES);
        reservation.setDate(LocalDate.now());
        reservation.setStart(LocalTime.of(10, 0));
        reservation.setEnd(LocalTime.of(11, 0));
        reservation.setPrice(BigDecimal.TEN);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setDeleted(false);

        CourtResponse courtResponse =
                mock(CourtResponse.class);

        CustomerResponse customerResponse =
                mock(CustomerResponse.class);

        when(courtMapper.toResponse(court)).thenReturn(courtResponse);
        when(customerMapper.toResponse(customer)).thenReturn(customerResponse);

        var response = reservationMapper.toResponse(reservation);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.court()).isEqualTo(courtResponse);
        assertThat(response.customer()).isEqualTo(customerResponse);
        assertThat(response.gameType()).isEqualTo(GameType.SINGLES);
        assertThat(response.deleted()).isFalse();
    }

    @Test
    void toEntity_mapsCorrectly() {
        ReservationCreateRequest request =
                new ReservationCreateRequest(
                        1L, "John", "123",
                        GameType.SINGLES,
                        LocalDate.now(),
                        LocalTime.of(10,0),
                        LocalTime.of(11,0)
                );

        Court court = new Court();
        Customer customer = new Customer();

        Reservation result =
                reservationMapper.toEntity(request, court, customer);

        assertThat(result.getCourt()).isEqualTo(court);
        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getGameType()).isEqualTo(GameType.SINGLES);
        assertThat(result.isDeleted()).isFalse();
    }

    @Test
    void updateEntity_updatesFields() {
        Reservation reservation = new Reservation();

        ReservationUpdateRequest request =
                new ReservationUpdateRequest(
                        1L, 2L, GameType.DOUBLES,
                        LocalDate.now(),
                        LocalTime.of(10,0),
                        LocalTime.of(11,0),
                        true
                );

        Court court = new Court();
        Customer customer = new Customer();

        reservationMapper.updateEntity(reservation, request, court, customer);

        assertThat(reservation.getCourt()).isEqualTo(court);
        assertThat(reservation.getCustomer()).isEqualTo(customer);
        assertThat(reservation.getGameType()).isEqualTo(GameType.DOUBLES);
        assertThat(reservation.isDeleted()).isTrue();
    }
}