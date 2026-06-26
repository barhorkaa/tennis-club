package cz.hornakova.barbora.tennisclub.model.dto;

import java.math.BigDecimal;

public record SurfaceTypeUpdateRequest(
        String name,
        BigDecimal pricePerMinute
) {
}