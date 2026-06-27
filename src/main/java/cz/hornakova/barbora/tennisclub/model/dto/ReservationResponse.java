package cz.hornakova.barbora.tennisclub.model.dto;

import cz.hornakova.barbora.tennisclub.model.entity.GameType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationResponse(
        long id,
        CourtResponse court,
        CustomerResponse customer,
        GameType gameType,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        BigDecimal price,
        LocalDateTime createdAt,
        boolean deleted
) {}
