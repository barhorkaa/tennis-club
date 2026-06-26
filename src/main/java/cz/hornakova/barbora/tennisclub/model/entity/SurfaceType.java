package cz.hornakova.barbora.tennisclub.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class SurfaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private BigDecimal pricePerMinute;

    private boolean deleted;

    public SurfaceType(String name, BigDecimal pricePerMinute) {
        this.name = name;
        this.pricePerMinute = pricePerMinute;
    }
}
