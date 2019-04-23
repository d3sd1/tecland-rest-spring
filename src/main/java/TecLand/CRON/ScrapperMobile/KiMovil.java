package TecLand.CRON.ScrapperMobile;

import TecLand.ORM.Model.ProductTemplateCommon;
import TecLand.ORM.Model.ProductTemplateMobile;

import TecLand.Logger.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;


@Component
public class KiMovil{

    @Autowired
    private LogService logger;

    private final String url = "https://www.kimovil.com/es/comparar-moviles";

    //@org.springframework.context.annotation.Profile({"dev", "test"})
    @Scheduled(fixedRate = 30000)
    public void ScrapperKiMovil(){
        if (getStatusConnectionCode(url) == 200) {

            Document document = getHtmlDocument(url);

            Elements entradas = document.getElementsByClass("item-wrap");
            String link = null;
            for (Element elem : entradas) {
                link = elem.getElementsByClass("device-link").attr("href");

                ScrapperMoviles(link);
            }
        }else{
            logger.warning("ScrapperMoviles: No conectando con KiMovil " + url);
        }


    }

    private  void ScrapperMoviles(String link){
        if (getStatusConnectionCode(link) == 200) {
            ProductTemplateMobile movil = new ProductTemplateMobile();

            Document document = getHtmlDocument(link);
            Elements specs = document.getElementsByClass("kifeatures clear");

            String[] size = null;
            for(Element elem : specs){
               size = elem.getElementsByClass("fc w30 inches").text().split("\"");
            }
            movil.setScreenSize(Float.parseFloat(size[0]));

        }else{
            logger.warning(" ScrapperMoviles[KiMovil] : No conectando con " + link);
        }
    }

    private static int getStatusConnectionCode(String url){
        Response response=null;
        try{
            response=Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
        }catch(Exception e){
            e.printStackTrace();
        }
            return response.statusCode();
    }

    private static Document getHtmlDocument(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
}