package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ProductBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false, unique = true)
    private String brandKeyName;

    @Column(nullable = false)
    private String brandFriendlyName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrandKeyName() {
        return brandKeyName;
    }

    public void setBrandKeyName(String brandKeyName) {
        this.brandKeyName = brandKeyName;
    }

    public String getBrandFriendlyName() {
        return brandFriendlyName;
    }

    public void setBrandFriendlyName(String brandFriendlyName) {
        this.brandFriendlyName = brandFriendlyName;
    }
}
