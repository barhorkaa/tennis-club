package cz.hornakova.barbora.tennisclub.model.dto;

import cz.hornakova.barbora.tennisclub.model.entity.GameType;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(
        long courtId,
        long customerId,
        GameType gameType,
        LocalDate date,
        LocalTime start,
        LocalTime end,
        boolean deleted
) {}
