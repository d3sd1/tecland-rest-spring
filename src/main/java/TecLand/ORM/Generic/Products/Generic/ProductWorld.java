package TecLand.ORM.Generic.Products.Generic;

import TecLand.ORM.Client.ClientUser;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ProductWorld {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne()
    private ClientUser user;

}
