package cz.hornakova.barbora.tennisclub.model.dto;

import java.math.BigDecimal;

public record SurfaceTypeResponse(
        Long id,
        String name,
        BigDecimal pricePerMinute
) {
}
