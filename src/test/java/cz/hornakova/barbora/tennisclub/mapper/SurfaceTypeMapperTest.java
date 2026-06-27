package cz.hornakova.barbora.tennisclub.mapper;

import cz.hornakova.barbora.tennisclub.model.dto.SurfaceTypeRequest;
import cz.hornakova.barbora.tennisclub.model.entity.SurfaceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SurfaceTypeMapperTest {

    private final SurfaceTypeMapper mapper = new SurfaceTypeMapper();

    @Test
    void toResponse_mapsCorrectly() {
        SurfaceType surface = new SurfaceType();
        surface.setId(1L);
        surface.setName("CLAY");
        surface.setPricePerMinute(BigDecimal.valueOf(10));

        var response = mapper.toResponse(surface);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("CLAY");
        assertThat(response.pricePerMinute()).isEqualTo(BigDecimal.valueOf(10));
    }

    @Test
    void toEntity_mapsCorrectly() {
        SurfaceTypeRequest request =
                new SurfaceTypeRequest("GRASS", BigDecimal.valueOf(20));

        SurfaceType entity = mapper.toEntity(request);

        assertThat(entity.getName()).isEqualTo("GRASS");
        assertThat(entity.getPricePerMinute()).isEqualTo(BigDecimal.valueOf(20));
    }

    @Test
    void updateEntity_updatesFields() {
        SurfaceType entity = new SurfaceType();
        entity.setName("OLD");
        entity.setPricePerMinute(BigDecimal.ONE);

        SurfaceTypeRequest request =
                new SurfaceTypeRequest("NEW", BigDecimal.TEN);

        mapper.updateEntity(entity, request);

        assertThat(entity.getName()).isEqualTo("NEW");
        assertThat(entity.getPricePerMinute()).isEqualTo(BigDecimal.TEN);
    }
}