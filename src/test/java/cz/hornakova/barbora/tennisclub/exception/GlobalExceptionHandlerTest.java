package cz.hornakova.barbora.tennisclub.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.hornakova.barbora.tennisclub.auth.JwtFilter;
import cz.hornakova.barbora.tennisclub.exception.helper.GlobalExceptionHandlerTestController;
import cz.hornakova.barbora.tennisclub.model.dto.TestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GlobalExceptionHandlerTestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {
        GlobalExceptionHandlerTestController.class,
        GlobalExceptionHandler.class
})
class GlobalExceptionHandlerTest {

    @MockitoBean
    JwtFilter jwtFilter;

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Test
    void customerNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/test/customer"))
                .andExpect(status().isNotFound());
    }

    @Test
    void courtNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/test/court"))
                .andExpect(status().isNotFound());
    }

    @Test
    void surfaceNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/test/surface"))
                .andExpect(status().isNotFound());
    }

    @Test
    void reservationNotFound_shouldReturn404() throws Exception {
        mockMvc.perform(get("/test/reservation"))
                .andExpect(status().isNotFound());
    }

    @Test
    void collision_shouldReturn400() throws Exception {
        mockMvc.perform(get("/test/collision"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidReservation_shouldReturn400() throws Exception {
        mockMvc.perform(get("/test/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid reservation"));
    }

    @Test
    void validation_error_shouldReturn400WithMap() throws Exception {

        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void invalidJson_shouldReturn400() throws Exception {

        mockMvc.perform(post("/test/validation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("INVALID_JSON"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Request body is missing or invalid"));
    }
}