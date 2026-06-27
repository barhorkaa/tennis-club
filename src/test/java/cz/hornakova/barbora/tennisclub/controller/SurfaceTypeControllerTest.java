package cz.hornakova.barbora.tennisclub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.service.SurfaceTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SurfaceTypeController.class)
class SurfaceTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SurfaceTypeService surfaceTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnList() throws Exception {
        when(surfaceTypeService.getAll()).thenReturn(List.of(
                new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40")),
                new SurfaceTypeResponse(2L, "Grass",  new BigDecimal("0.35"))
        ));

        mockMvc.perform(get("/api/surface-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getById_shouldReturnSurfaceType() throws Exception {
        when(surfaceTypeService.getById(1L))
                .thenReturn(new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40")));

        mockMvc.perform(get("/api/surface-types/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Clay"));
    }

    @Test
    void create_shouldReturnCreated() throws Exception {
        SurfaceTypeRequest request = new SurfaceTypeRequest("Clay", new BigDecimal("0.40"));

        when(surfaceTypeService.create(any()))
                .thenReturn(new SurfaceTypeResponse(1L, "Clay", new BigDecimal("0.40")));

        mockMvc.perform(post("/api/surface-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void update_shouldReturnUpdated() throws Exception {
        SurfaceTypeRequest request = new SurfaceTypeRequest("Hard", new BigDecimal("0.35"));

        when(surfaceTypeService.update(eq(1L), any()))
                .thenReturn(new SurfaceTypeResponse(1L, "Hard", new BigDecimal("0.35")));

        mockMvc.perform(put("/api/surface-types/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hard"));
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        doNothing().when(surfaceTypeService).delete(1L);

        mockMvc.perform(delete("/api/surface-types/1"))
                .andExpect(status().isOk());
    }
}
