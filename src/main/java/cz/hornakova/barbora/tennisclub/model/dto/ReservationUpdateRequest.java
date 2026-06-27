package cz.hornakova.barbora.tennisclub.model.dto;

import cz.hornakova.barbora.tennisclub.model.entity.GameType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(

        @NotNull
        long courtId,

        @NotNull
        long customerId,

        @NotNull
        GameType gameType,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime start,

        @NotNull
        LocalTime end,

        boolean deleted
) {}
