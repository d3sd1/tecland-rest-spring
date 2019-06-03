package TecLand.ORM.Generic;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Transactional
@Table()
@EntityListeners(AuditingEntityListener.class)
public class CurrencyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @OneToOne()
    private CurrencyValue baseCurrency;

    @Column(nullable = false, unique = true)
    private String keyName;

    @Column(nullable = false)
    private float currencyExchangeValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CurrencyValue getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyValue baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public float getCurrencyExchangeValue() {
        return currencyExchangeValue;
    }

    public void setCurrencyExchangeValue(float currencyExchangeValue) {
        this.currencyExchangeValue = currencyExchangeValue;
    }

    @Override
    public String toString() {
        return "CurrencyValue{" +
                "id=" + id +
                ", baseCurrency=" + baseCurrency +
                ", keyName='" + keyName + '\'' +
                ", currencyExchangeValue=" + currencyExchangeValue +
                '}';
    }
}
