package TecLand.ORM.Generic.Products.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


// Stores things such as Bluetooth, OTG, WI-FI...
@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Conectivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private float keyName;

}
