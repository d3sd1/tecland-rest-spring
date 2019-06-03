package TecLand.ORM.Generic.Products.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne
    private Brand modelBrand;

    @Column(nullable = false)
    private String modelSerial;

    @Column(nullable = false)
    private String modelKeyName;

    @Column(nullable = false)
    private String modelFriendlyName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Brand getModelBrand() {
        return modelBrand;
    }

    public void setModelBrand(Brand modelBrand) {
        this.modelBrand = modelBrand;
    }

    public String getModelSerial() {
        return modelSerial;
    }

    public void setModelSerial(String modelSerial) {
        this.modelSerial = modelSerial;
    }

    public String getModelKeyName() {
        return modelKeyName;
    }

    public void setModelKeyName(String modelKeyName) {
        this.modelKeyName = modelKeyName;
    }

    public String getModelFriendlyName() {
        return modelFriendlyName;
    }

    public void setModelFriendlyName(String modelFriendlyName) {
        this.modelFriendlyName = modelFriendlyName;
    }
}
