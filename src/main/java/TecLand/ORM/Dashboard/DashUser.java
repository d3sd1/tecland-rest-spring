package TecLand.ORM.Dashboard;

import TecLand.ORM.Generic.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class DashUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surnames;

    @Column(nullable = false)
    private int telephone;

    @Column(nullable = true)
    private String lastVisitPage;

    @OneToOne()
    private Country country;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @OneToOne()
    private DashTheme dashTheme;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<DashPermission> permissions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public DashTheme getDashTheme() {
        return dashTheme;
    }

    public void setDashTheme(DashTheme dashTheme) {
        this.dashTheme = dashTheme;
    }

    public Collection<DashPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Collection<DashPermission> permissions) {
        this.permissions = permissions;
    }

    public String getLastVisitPage() {
        return lastVisitPage;
    }

    public void setLastVisitPage(String lastVisitPage) {
        this.lastVisitPage = lastVisitPage;
    }

    @Override
    public String toString() {
        return "DashUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surnames='" + surnames + '\'' +
                ", telephone=" + telephone +
                ", country=" + country +
                ", password='" + password + '\'' +
                ", dashTheme=" + dashTheme +
                ", permissions=" + permissions +
                '}';
    }
}
