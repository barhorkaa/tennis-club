package cz.hornakova.barbora.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.hornakova.barbora.tennisclub.auth.JwtFilter;
import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.GameType;
import cz.hornakova.barbora.tennisclub.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservationControllerTest {

    @MockitoBean JwtFilter jwtFilter;
    @MockitoBean ReservationService reservationService;

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void create_shouldReturnOk() throws Exception {
        ReservationCreateRequest req = new ReservationCreateRequest(
                1L, "John", "123",
                GameType.SINGLES,
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        when(reservationService.create(any()))
                .thenReturn(mock(ReservationResponse.class));

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        verify(reservationService).create(any());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        ReservationUpdateRequest req = new ReservationUpdateRequest(
                1L, 2L, GameType.DOUBLES,
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0),
                false
        );

        when(reservationService.update(eq(5L), any()))
                .thenReturn(mock(ReservationResponse.class));

        mockMvc.perform(put("/api/reservations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        verify(reservationService).update(eq(5L), any());
    }

    @Test
    void getByCourtId_shouldReturnList() throws Exception {
        when(reservationService.getByCourtId(1L))
                .thenReturn(List.of(mock(ReservationResponse.class)));

        mockMvc.perform(get("/api/reservations/court/1"))
                .andExpect(status().isOk());

        verify(reservationService).getByCourtId(1L);
    }

    @Test
    void getByCustomerPhone_defaultOnlyFutureFalse() throws Exception {
        when(reservationService.getByCustomerPhone("123", false))
                .thenReturn(List.of(mock(ReservationResponse.class)));

        mockMvc.perform(get("/api/reservations/customer/123"))
                .andExpect(status().isOk());

        verify(reservationService).getByCustomerPhone("123", false);
    }

    @Test
    void getByCustomerPhone_onlyFutureTrue() throws Exception {
        when(reservationService.getByCustomerPhone("123", true))
                .thenReturn(List.of(mock(ReservationResponse.class)));

        mockMvc.perform(get("/api/reservations/customer/123")
                        .param("onlyFuture", "true"))
                .andExpect(status().isOk());

        verify(reservationService).getByCustomerPhone("123", true);
    }

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(reservationService.getAll())
                .thenReturn(List.of(mock(ReservationResponse.class)));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk());

        verify(reservationService).getAll();
    }

    @Test
    void getById_shouldReturnItem() throws Exception {
        when(reservationService.getById(10L))
                .thenReturn(mock(ReservationResponse.class));

        mockMvc.perform(get("/api/reservations/10"))
                .andExpect(status().isOk());

        verify(reservationService).getById(10L);
    }

    @Test
    void delete_shouldCallService() throws Exception {
        doNothing().when(reservationService).delete(7L);

        mockMvc.perform(delete("/api/reservations/7"))
                .andExpect(status().isOk());

        verify(reservationService).delete(7L);
    }
}