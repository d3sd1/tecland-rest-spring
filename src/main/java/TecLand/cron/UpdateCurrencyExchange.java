package TecLand.cron;

import TecLand.ORM.Model.CurrencyValue;
import TecLand.ORM.Repository.CurrencyRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class UpdateCurrencyExchange {

    @Autowired
    private CurrencyRepository currencyRepository;


    @Scheduled(fixedRate = 30000) // Every 30S (30.000 ms), w/ beggining
    public void updateCurrencies() {
        System.out.println("Updating currencies...");

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://api.exchangeratesapi.io/latest?base=EUR", HttpMethod.GET, requestEntity, String.class);
        HttpStatus status = responseEntity.getStatusCode();
        if (status.is2xxSuccessful()) {
            System.out.println("Currency rate exchange updated.");

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> obj = new HashMap<String, Object>();

            try {
                obj = mapper.readValue(responseEntity.getBody(), new TypeReference<Map<String, Object>>() {
                });
                HashMap<String, Double> rates = (HashMap<String, Double>) obj.get("rates");

                /* Update base currency at first, then remote it of the iteration */
                String baseRateName = obj.get("base").toString();
                float baseRateVal = 1;
                CurrencyValue baseCurrency = this.updateCurrencyDb(baseRateName, baseRateVal, null);

                /* Now iterate over the rest and update them */
                for (Map.Entry<String, Double> rate : rates.entrySet()) {
                    this.updateCurrencyDb(rate.getKey(), rate.getValue(), baseCurrency);

                }

            } catch (Exception e) {
                System.out.println("Currency update error: ");
                e.printStackTrace();
            }
        } else {
            System.out.println("Currency rate exchange update error: " + status);
        }
    }


    private CurrencyValue updateCurrencyDb(String baseRateName, double baseRateVal, CurrencyValue baseCurrency) {
        CurrencyValue baseCurencyDb = this.currencyRepository.findByKeyName(baseRateName);
        if (null == baseCurencyDb) {
            baseCurencyDb = new CurrencyValue();
        }
        baseCurencyDb.setKeyName(baseRateName);
        baseCurencyDb.setBaseCurrency(baseCurrency);
        baseCurencyDb.setCurrencyExchangeValue((float) baseRateVal);
        this.currencyRepository.save(baseCurencyDb);
        return baseCurencyDb;
    }
}
