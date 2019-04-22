package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class DashPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String permissionKey;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    @Override
    public String toString() {
        return "DashPermission{" +
                "id=" + id +
                ", permissionKey='" + permissionKey + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DashPermission) {
            DashPermission toCompare = (DashPermission) o;
            return this.id == toCompare.id;
        }
        return false;
    }
}
