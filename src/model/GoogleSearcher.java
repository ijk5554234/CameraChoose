package model;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GoogleSearcher {
    public static int googleSearch(String kw) throws IOException{       
        Document doc = null;
        doc = Jsoup.connect("https://www.google.com/search?q=" + kw)
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.17 (KHTML, like Gecko)Chrome/24.0.1312.56 Safari/537.17")
                .timeout(3000).get();
        String result = doc.select("div#resultStats").text().toString().split("[(]")[0];
        Pattern p = Pattern.compile("[^0-9]"); 
        Matcher m = p.matcher(result);
        int num = Integer.parseInt(m.replaceAll(""));
        return num;
    }
    public static void main(String[] args) throws IOException{
        System.out.println(googleSearch("Nikon"));
    }
}
