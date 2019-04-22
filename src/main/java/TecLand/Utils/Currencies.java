package TecLand.Utils;

import TecLand.ORM.Model.CurrencyValue;

public class Currencies {
    public float convert(CurrencyValue currency1, CurrencyValue currency2) {
       return currency1.getCurrencyExchangeValue()*1/currency2.getCurrencyExchangeValue();
    }

    public float[] convertNCurrencies(CurrencyValue[] currencies, CurrencyValue currency2) {
        float[] currenciesValues = new float[currencies.length];
        for (int i = 0; i < currencies.length; i++) {
            currenciesValues[i] = convert(currencies[i], currency2);
        }
        return currenciesValues;
    }

    public float applyPercentage(float price, float discount) {
        return price * (1 + discount / 100);
    }
}
