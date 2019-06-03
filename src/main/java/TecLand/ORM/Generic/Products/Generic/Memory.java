package TecLand.ORM.Generic.Products.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(nullable = false)
    private float keyName;

    @OneToOne()
    private MemoryType type;

    @Column(nullable = false)
    private float speedGbps;

}
