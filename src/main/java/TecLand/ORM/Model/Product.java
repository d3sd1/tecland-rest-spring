package TecLand.ORM.Model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Product {
    //TODO: incluir accesorios del producto
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne()
    private ClientUser user;

    /*
    NOTA: Para sacar la plantilla del producto, se coge de productType el tipo  (por ejemplo ProductTemplateMobile), y luego
    con el productReferenceId puedes ir a la tabla directamente (por ejemplo ProductTemplateMobile) y buscar por dicha Id.
     */
    @OneToOne()
    private ProductType productType;

    @Column(nullable = false)
    private int productReferenceId;

    @Column(nullable = false)
    private String customPhoto;

    @Column(nullable = false)
    private float priceBaseCurrency;

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

    public String getCustomPhoto() {
        return customPhoto;
    }

    public void setCustomPhoto(String customPhoto) {
        this.customPhoto = customPhoto;
    }

    public boolean isAcceptReturns() {
        return acceptReturns;
    }

    public void setAcceptReturns(boolean acceptReturns) {
        this.acceptReturns = acceptReturns;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getProductReferenceId() {
        return productReferenceId;
    }

    public void setProductReferenceId(int productReferenceId) {
        this.productReferenceId = productReferenceId;
    }
}
