package TecLand.utils;

import TecLand.ORM.Model.CurrencyValue;
import TecLand.ORM.Repository.CurrencyRepository;

public class Currencies {
    public float convert(CurrencyValue currency1, CurrencyValue currency2) {
       return currency1.getCurrencyExchangeValue()*1/currency2.getCurrencyExchangeValue();
    }
}
