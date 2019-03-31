package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class DashUserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private DashUser dashUser;

    @Column(nullable = false, unique = true)
    private String jwt;

    @Column(nullable = false)
    private Timestamp expended;

    @Column(nullable = false)
    private Timestamp expires;

    @Column(nullable = false)
    private float coordsLat;

    @Column(nullable = false)
    private float coordsLng;

    @Column(nullable = false)
    private String hash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DashUser getDashUser() {
        return dashUser;
    }

    public void setDashUser(DashUser dashUser) {
        this.dashUser = dashUser;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Timestamp getExpended() {
        return expended;
    }

    public void setExpended(Timestamp expended) {
        this.expended = expended;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "DashUserLogin{" +
                "id=" + id +
                ", dashUser=" + dashUser +
                ", jwt='" + jwt + '\'' +
                ", expended=" + expended +
                ", expires=" + expires +
                ", coordsLat=" + coordsLat +
                ", coordsLng=" + coordsLng +
                '}';
    }
}
