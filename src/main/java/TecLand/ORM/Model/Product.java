package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne()
    private ClientUser user;

    @OneToOne()
    private ProductTemplate productTemplate;

    @Column(nullable = false)
    private String customPhoto;

    @Column(nullable = false)
    private float priceEUR;

    @Column(nullable = false)
    private boolean acceptReturns;

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

    public ProductTemplate getProductTemplate() {
        return productTemplate;
    }

    public void setProductTemplate(ProductTemplate productTemplate) {
        this.productTemplate = productTemplate;
    }

    public String getCustomPhoto() {
        return customPhoto;
    }

    public void setCustomPhoto(String customPhoto) {
        this.customPhoto = customPhoto;
    }

    public float getPriceEUR() {
        return priceEUR;
    }

    public void setPriceEUR(float priceEUR) {
        this.priceEUR = priceEUR;
    }

    public boolean isAcceptReturns() {
        return acceptReturns;
    }

    public void setAcceptReturns(boolean acceptReturns) {
        this.acceptReturns = acceptReturns;
    }
}
