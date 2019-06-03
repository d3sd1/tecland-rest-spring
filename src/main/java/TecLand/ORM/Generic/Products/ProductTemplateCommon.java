package TecLand.ORM.Generic.Products;

import TecLand.ORM.Generic.Coords;
import TecLand.ORM.Generic.Products.Generic.*;

import javax.persistence.*;

@MappedSuperclass
public class ProductTemplateCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private Coords productCoords;

    @OneToOne()
    private ProductWorld productWorld;

    @OneToOne()
    private ProductStatus productStatus;

    @OneToOne()
    private ProductBrand productBrand;

    @OneToOne()
    private ProductOS productOperatingSystem;

    @OneToOne()
    private ProductModel productModel;

    @Column(nullable = false)
    private float weight = 0;

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

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public ProductBrand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(ProductBrand productBrand) {
        this.productBrand = productBrand;
    }
}