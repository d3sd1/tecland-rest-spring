package TecLand.ORM.Generic.Products.SuperType;

import TecLand.ORM.Generic.Coords;
import TecLand.ORM.Generic.Products.Generic.*;

import javax.persistence.*;
import java.util.List;

@MappedSuperclass
public class CommonProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private Coords productCoords;

    @OneToOne()
    private ProductWorld productWorld;

    @OneToOne()
    private Brand brand;

    @OneToOne()
    private ProductOS productOperatingSystem;

    @OneToOne()
    private ProductModel productModel;

    @Column(nullable = false)
    private float weight = 0;

    @Column(nullable = false)
    private float screenSize = 0;

    @OneToOne()
    private ScreenType screenType;

    @OneToOne()
    private Battery battery;

    @OneToOne()
    private Memory ram;

    @OneToOne()
    private Memory rom;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Camera.class)
    @JoinColumn(name = "id")
    private List<Camera> cameras;

    public void setWeight(float weight){
        if(weight<0){
            weight *= -1;
        }
        this.weight = weight;
    }

    public float getWeight(){
        return this.weight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Coords getProductCoords() {
        return productCoords;
    }

    public void setProductCoords(Coords productCoords) {
        this.productCoords = productCoords;
    }

    public ProductWorld getProductWorld() {
        return productWorld;
    }

    public void setProductWorld(ProductWorld productWorld) {
        this.productWorld = productWorld;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
