package TecLand.ORM;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false, unique = true, length = 2)
    private String alpha2;

    @Column(nullable = false, unique = true, length = 3)
    private String alpha3;

    @Column(nullable = false, unique = true, length = 3)
    private short countryCode;

    @Column(nullable = false, unique = true, length = 3)
    private String iso3166_2;

    @OneToOne()
    private Region region;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(short countryCode) {
        this.countryCode = countryCode;
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
}
