package TecLand.Utils;

import TecLand.ORM.Model.CurrencyValue;

public class Currencies {
    public float convert(CurrencyValue currency1, CurrencyValue currency2) {
       return currency1.getCurrencyExchangeValue()*1/currency2.getCurrencyExchangeValue();
    }
}
