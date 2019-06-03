package TecLand.ORM.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true, length = 2)
    private String alpha2;

    @Column(nullable = false, unique = true, length = 3)
    private String alpha3;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false, unique = true)
    private String iso3166_2;

    @OneToOne()
    private Region region;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIso3166_2() {
        return iso3166_2;
    }

    public void setIso3166_2(String iso3166_2) {
        this.iso3166_2 = iso3166_2;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", alpha2='" + alpha2 + '\'' +
                ", alpha3='" + alpha3 + '\'' +
                ", displayName='" + displayName + '\'' +
                ", iso3166_2='" + iso3166_2 + '\'' +
                ", region=" + region +
                '}';
    }
}
