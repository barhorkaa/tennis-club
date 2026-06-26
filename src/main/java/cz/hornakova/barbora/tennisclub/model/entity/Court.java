package cz.hornakova.barbora.tennisclub.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "surface_type_id")
    private SurfaceType surfaceType;

    public Court(SurfaceType surfaceType, String name) {
        this.surfaceType = surfaceType;
        this.name = name;
    }
}
