package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bitdouble {

    public List<Element> loadElements(String date) throws IOException {
        List<Element> elementList=new ArrayList<>();
        Document doc= null;
        doc = Jsoup.connect("https://bitdouble.io/pf.php?day="+date).get();
        Elements elements=doc.getElementsByTag("tr");

        for (int i = elements.size()-1; i >=0 ; i--) {
            elements.get(i).getElementsByTag("td").forEach(element -> {
                if(element.toString().contains("ball"))
                    elementList.add(element);
            });
        }
        return elementList;
    }
}
