package TecLand.ORM.Generic.Products.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Proccesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private String keyName;

    @Column(nullable = false)
    private float bits;

    @OneToOne
    private Brand brand;

    @Column(nullable = false)
    private float clockMinGhz;

    @Column(nullable = false)
    private float clockMaxGhz;

    @Column(nullable = false)
    private boolean allowOverclock;

}
