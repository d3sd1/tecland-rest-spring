package TecLand.ORM.Client;

import TecLand.ORM.Generic.Coords;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ClientUserLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private ClientUser user;

    @Column(nullable = false, unique = true)
    private String jwt;

    @Column(nullable = false, unique = true)
    private Timestamp expended;

    @Column(nullable = false, unique = true)
    private Timestamp expires;

    @OneToOne()
    private Coords loginCoords;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClientUser getUser() {
        return user;
    }

    public void setUser(ClientUser user) {
        this.user = user;
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

    public Coords getLoginCoords() {
        return loginCoords;
    }

    public void setLoginCoords(Coords loginCoords) {
        this.loginCoords = loginCoords;
    }

    @Override
    public String toString() {
        return "ClientUserLogin{" +
                "id=" + id +
                ", user=" + user +
                ", jwt='" + jwt + '\'' +
                ", expended=" + expended +
                ", expires=" + expires +
                ", loginCoords=" + loginCoords +
                '}';
    }
}
