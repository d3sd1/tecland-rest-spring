package TecLand.ORM.Generic.Products.Templates;

import TecLand.ORM.Generic.Products.ProductTemplateCommon;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table()
@EntityListeners(AuditingEntityListener.class)
public class ProductTemplateMobile extends ProductTemplateCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column()
    private float screenSize;

    public void setScreenSize(float size){
        if(size<0){
            size*=-1;
        }
        this.screenSize = size;
    }

    public float getScreenSize(){
        return this.screenSize;
    }
}