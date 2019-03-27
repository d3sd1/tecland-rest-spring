package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String statusKeyName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatusKeyName() {
        return statusKeyName;
    }

    public void setStatusKeyName(String statusKeyName) {
        this.statusKeyName = statusKeyName;
    }

    @Override
    public String toString() {
        return "ProductStatus{" +
                "id=" + id +
                ", statusKeyName='" + statusKeyName + '\'' +
                '}';
    }
}
