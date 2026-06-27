package cz.hornakova.barbora.tennisclub.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "court_id")
    private Court court;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime start;

    @Column(name = "end_time")
    private LocalTime end;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // This approach freezes the price at the time of making the reservation
    // Since the price of the court can change, this maintains historical accuracy
    // Could be solved as a separate SurfacePriceHistory table, this is the simplest solve
    private BigDecimal price;

    private boolean deleted;

    public Reservation(Court court, Customer customer, GameType gameType, LocalDate date, LocalTime start, LocalTime end) {
        this.court = court;
        this.customer = customer;
        this.gameType = gameType;
        this.date = date;
        this.start = start;
        this.end = end;
    }
}
