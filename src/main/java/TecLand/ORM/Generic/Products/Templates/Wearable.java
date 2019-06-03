package TecLand.ORM.Generic.Products.Templates;

import TecLand.ORM.Generic.Products.Generic.WearableType;
import TecLand.ORM.Generic.Products.SuperType.CommonProduct;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Wearable extends CommonProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    // Android, iPad, eBook...
    @OneToOne()
    private WearableType type;
}
