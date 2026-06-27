package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.CourtRequest;
import cz.hornakova.barbora.tennisclub.model.entity.Court;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CourtMapperTest {

    @Mock
    private SurfaceTypeMapper surfaceTypeMapper;

    @InjectMocks
    private CourtMapper courtMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void toResponse_mapsCorrectly() {
        SurfaceType surface = new SurfaceType();
        surface.setId(1L);

        Court court = new Court();
        court.setId(10L);
        court.setName("Court A");
        court.setSurfaceType(surface);

        SurfaceTypeResponse surfaceResponse =
                new SurfaceTypeResponse(1L, "CLAY", null);

        when(surfaceTypeMapper.toResponse(surface)).thenReturn(surfaceResponse);

        var response = courtMapper.toResponse(court);

        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("Court A");
        assertThat(response.surfaceType()).isEqualTo(surfaceResponse);
    }

    @Test
    void toEntity_mapsCorrectly() {
        CourtRequest request = new CourtRequest("Court A", 1L);

        SurfaceType surface = new SurfaceType();

        Court result = courtMapper.toEntity(request, surface);

        assertThat(result.getName()).isEqualTo("Court A");
        assertThat(result.getSurfaceType()).isEqualTo(surface);
    }

    @Test
    void updateEntity_updatesFields() {
        SurfaceType oldSurface = new SurfaceType();
        SurfaceType newSurface = new SurfaceType();

        Court court = new Court(oldSurface, "Old");

        CourtRequest request = new CourtRequest("New", 2L);

        courtMapper.updateEntity(court, request, newSurface);

        assertThat(court.getName()).isEqualTo("New");
        assertThat(court.getSurfaceType()).isEqualTo(newSurface);
    }
}