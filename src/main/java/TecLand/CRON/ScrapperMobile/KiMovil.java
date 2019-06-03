package TecLand.CRON.ScrapperMobile;

import TecLand.Logger.LogService;
import TecLand.ORM.Generic.Products.Templates.Mobile;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KiMovil{

    @Autowired
    private LogService logger;

    private final String url = "https://www.kimovil.com/es/comparar-moviles";

    //@org.springframework.context.annotation.Profile({"dev", "test"}) <- esto es importante dejarlo chicos :)
    //@Scheduled(fixedRate = 30000) //desactivado por andrei. tiene error y sale ERROR en el debugger!!
    public void ScrapperKiMovil(){
        if (getStatusConnectionCode(url) == 200) {
            logger.info("ScrapperMoviles: Conectando con KiMovil");
            Document document = getHtmlDocument(url);

            Elements entradas = document.getElementById("results").getElementsByClass("item-wrap");
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
            Mobile movil = new Mobile();

            Document document = getHtmlDocument(link);
            Element specs = document.getElementsByClass("kifeatures clear").get(0);
            Element extra = document.getElementById("about").getElementsByClass("cell right").get(0);
            Element extra_2 = document.getElementById("design").child(1).getElementsByClass("cell right").get(1);
            Elements camera = document.getElementById("camera").getElementsByClass("cell right");
            Elements conectividad = document.getElementById("connectivity").getElementsByClass("row");

            String[] screen_size = null;
            String[] weight = null;
            String[] brand = null;
            String[] battery = null;
            String[] ram = null;
            String[] memory = null;
            String[] dimensions = null;
            String[] resolution_frontcamera = null;
            String resolution = null;
            String screen_tec = null;
            String dimensions_aux = "";
            String grosor = null;
            String resolution_frontcamera_aux = null;
            String[] resolution_backcamera = null;
            String[] bluetooth = null;
            String wifi = null;
            boolean dualSim = false;
            boolean NFC = false;

            screen_size = specs.getElementsByClass("fc w30 inches").text().split("\"");
            weight = specs.getElementsByClass("fc w100 ram").text().split(" ");
            battery = specs.getElementsByClass("fc w100 battery").text().split(" ");
            ram = specs.getElementsByClass("fc w100 ram").text().split("GB");
            memory = specs.getElementsByClass("fc w100 storage").text().split("GB");
            brand = extra.child(0).child(1).text().split(" ");

            for(Element elem : specs.getElementsByClass("fc w30")){
                dimensions_aux+= elem.text() + " ";
            }
            dimensions = dimensions_aux.split("mm");
            grosor = dimensions[2].trim();
            dimensions_aux = "";
            for(int i = 0; i< dimensions.length; i++){
                dimensions_aux += dimensions[i].trim();
                if(i < dimensions.length -2){
                    dimensions_aux += " x ";
                }
            }

            screen_tec = extra_2.child(0).child(3).text();
            resolution = extra_2.child(0).child(5).text();
            resolution_frontcamera_aux = camera.get(0).child(1).child(1).text();
            if(!resolution_frontcamera_aux.contains("Mpx")){
                resolution_frontcamera_aux = camera.get(0).child(0).child(1).text();
            }
            resolution_frontcamera = resolution_frontcamera_aux.split("Mpx");

            resolution_backcamera = camera.get(1).child(0).child(1).text().split("Mpx");

            bluetooth = conectividad.get(4).child(1).child(0).child(1).text().split("Bajo");
            wifi = conectividad.get(3).child(1).child(0).child(1).text();
            if(!conectividad.get(8).child(1).child(0).child(1).text().trim().contains("No")){
                NFC = true;
            }
            if(conectividad.get(2).child(1).child(0).child(1).text().contains("Dual")){
                dualSim = true;
            }

            movil.setWeight(Float.parseFloat(weight[1]));
            //movil.setScreenSize(Float.parseFloat(screen_size[0]));
            //logger.info(movil.getScreenSize() + " " + movil.getWeight() + " " + brand[0] + " " + battery[0] + " " + ram[0] + " "+ memory[0] + " " + resolution + " "+ screen_tec + " " + dimensions_aux + " " + grosor + " " + resolution_frontcamera[0] + " " + resolution_backcamera[0] + " " + bluetooth[0] + " " + wifi + " " + NFC + " " + dualSim);
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