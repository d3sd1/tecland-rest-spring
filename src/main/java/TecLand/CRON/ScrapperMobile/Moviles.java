package TecLand.CRON.ScrapperMobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import TecLand.Logger.LogService;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Component
public class Moviles {

    private static final String url = "https://www.moviles.com/huawei/p30-lite/caracteristicas-detalle";

    @Profile({"dev","test"})
    @Scheduled(fixedDelay = 5000)//Every 5000ms or 5s
    public void ScrapperMovilZona(){

        if(getStatusConnectionCode(url) == 200){

            Document doc = getHtmlDocument(url);

            if(doc != null){

                Elements caracteristicas = doc.select("div.listado-caracteristicas-detalle");
            }
        }

    }

    private int getStatusConnectionCode(String url){

        int code = 0;

        try{
            code = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute().statusCode();
        }catch (Exception e){
            e.printStackTrace();
        }
        return code;
    }

    private Document getHtmlDocument(String url){
        Document doc = null;
        try{
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return doc;
    }
}

