package cz.hornakova.barbora.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.hornakova.barbora.tennisclub.model.dto.*;
import cz.hornakova.barbora.tennisclub.model.entity.GameType;
import cz.hornakova.barbora.tennisclub.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean ReservationService reservationService;

    @Test
    void create_shouldPassValidDto() throws Exception {
        ReservationCreateRequest req = new ReservationCreateRequest(
                1L,
                "John",
                "123",
                GameType.SINGLES,
                LocalDate.now(),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        when(reservationService.create(any()))
                .thenReturn(mock(ReservationResponse.class));

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        verify(reservationService).create(any());
    }

    @Test
    void update_shouldBindAllFields() throws Exception {
        ReservationUpdateRequest req = new ReservationUpdateRequest(
                1L, 2L, GameType.DOUBLES,
                LocalDate.now(),
                LocalTime.of(10,0),
                LocalTime.of(11,0),
                false
        );

        when(reservationService.update(eq(5L), any()))
                .thenReturn(mock(ReservationResponse.class));

        mockMvc.perform(put("/reservations/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }
}
