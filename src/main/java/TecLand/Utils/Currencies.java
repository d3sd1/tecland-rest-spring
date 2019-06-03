package TecLand.Utils;

import TecLand.ORM.Generic.CurrencyValue;

public class Currencies {
    public float convert(CurrencyValue currency1, CurrencyValue currency2) {
       return currency1.getCurrencyExchangeValue()*1/currency2.getCurrencyExchangeValue();
    }

    public float applyDiscount(float price, float discount) {
        return price * (1 - discount / 100);
    }

    public float applyTax(float price, float tax) {
        return price * (1 + tax / 100);
    }
}
