package cz.hornakova.barbora.tennisclub.model.dto;

import cz.hornakova.barbora.tennisclub.model.entity.GameType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(

        @NotNull
        Long courtId,

        @NotBlank
        String customerName,

        @NotBlank
        String customerPhone,

        @NotNull
        GameType gameType,

        @NotNull
        LocalDate date,

        @NotNull
        LocalTime start,

        @NotNull
        LocalTime end)
{}
