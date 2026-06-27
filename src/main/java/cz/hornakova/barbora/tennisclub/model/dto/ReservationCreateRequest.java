package cz.hornakova.barbora.tennisclub.model.dto;

import cz.hornakova.barbora.tennisclub.model.entity.GameType;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        long courtId,
        String customerName,
        String customerPhone,
        GameType gameType,
        LocalDate date,
        LocalTime start,
        LocalTime end
) {}
