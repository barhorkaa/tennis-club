package cz.hornakova.barbora.tennisclub.model.dto;

import java.math.BigDecimal;

public record SurfaceTypeCreateRequest(
        String name,
        BigDecimal pricePerMinute
) {
}
