package TecLand.ORM.Generic.Products.SuperType;

import TecLand.ORM.Generic.Coords;

import javax.persistence.*;


// usar esto para portatiles, tablets, wearables... y meter aqui la conectividad, tamaño pantallaza y eso. (o crear otra clase  conectividad)
@MappedSuperclass
public class PortableProduct extends CommonProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private Coords productCoords;

    @Column(nullable = false)
    private byte simPorts;

}
