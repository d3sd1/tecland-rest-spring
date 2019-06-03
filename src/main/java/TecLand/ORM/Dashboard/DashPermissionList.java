package TecLand.ORM.Dashboard;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class DashPermissionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String keyName;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "list_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Collection<DashPermission> permissions = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String toString() {
        return "DashPermissionList{" +
                "id=" + id +
                ", keyName='" + keyName + '\'' +
                '}';
    }
}
