package TecLand.ORM.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Coords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private float coordsLat;

    @Column(nullable = false, unique = true)
    private float coordsLng;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getCoordsLat() {
        return coordsLat;
    }

    public void setCoordsLat(float coordsLat) {
        this.coordsLat = coordsLat;
    }

    public float getCoordsLng() {
        return coordsLng;
    }

    public void setCoordsLng(float coordsLng) {
        this.coordsLng = coordsLng;
    }
}
