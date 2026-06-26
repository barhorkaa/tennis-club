package cz.hornakova.barbora.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.hornakova.barbora.tennisclub.model.dto.CourtCreateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.CourtResponse;
import cz.hornakova.barbora.tennisclub.model.dto.CourtUpdateRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.service.CourtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(CourtController.class)
class CourtControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourtService courtService;

    @Autowired
    private ObjectMapper objectMapper;

    private SurfaceTypeResponse surfaceType() {
        return new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.35"));
    }

    @Test
    void shouldReturnAllCourts() throws Exception {

        List<CourtResponse> response = List.of(
                new CourtResponse(1L, "Court A", surfaceType()),
                new CourtResponse(2L, "Court B", surfaceType())
        );

        when(courtService.getAll()).thenReturn(response);

        mockMvc.perform(get("/api/courts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].surfaceType.id").value(1L))
                .andExpect(jsonPath("$[0].surfaceType.name").value("Clay"));

        verify(courtService).getAll();
    }

    @Test
    void shouldReturnCourtById() throws Exception {

        CourtResponse response =
                new CourtResponse(1L, "Court A", surfaceType());

        when(courtService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/courts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Court A"))
                .andExpect(jsonPath("$.surfaceType.name").value("Clay"));

        verify(courtService).getById(1L);
    }

    @Test
    void shouldCreateCourt() throws Exception {

        CourtCreateRequest request =
                new CourtCreateRequest("Court A", 10L);

        CourtResponse response =
                new CourtResponse(1L, "Court A", surfaceType());

        when(courtService.create(any(CourtCreateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/courts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.surfaceType.id").value(1L));

        verify(courtService).create(any(CourtCreateRequest.class));
    }

    @Test
    void shouldUpdateCourt() throws Exception {

        CourtUpdateRequest request =
                new CourtUpdateRequest("Updated Court", 20L);

        CourtResponse response =
                new CourtResponse(1L, "Updated Court", surfaceType());

        when(courtService.update(eq(1L), any(CourtUpdateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/courts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Court"))
                .andExpect(jsonPath("$.surfaceType.name").value("Clay"));

        verify(courtService).update(eq(1L), any(CourtUpdateRequest.class));
    }

    @Test
    void shouldDeleteCourt() throws Exception {

        doNothing().when(courtService).delete(1L);

        mockMvc.perform(delete("/api/courts/1"))
                .andExpect(status().isOk());

        verify(courtService).delete(1L);
    }
}
