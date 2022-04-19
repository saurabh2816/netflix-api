package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.model.Node;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class JSoupService {

    private static String res;

    public List<Node> getListOfNodesFromLink() {

        String url = "http://167.114.174.132:9092/movies/Batch212/";
        List<Node> resultList = new ArrayList<>();

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");


        // REGEX
        final String regex = "([\\.\\w']+?)(\\.[0-9]{4}\\..*)";
        final Pattern pattern = Pattern.compile(regex);

        for (Element link : links) {

            String decodedUrl="";

            // decode the href URL becuase it was failing for  Bridget.Jones%27s.Baby.2016.720p.BluRay.x264-%5BYTS.AG%5D.mp4
            try {
                decodedUrl = URLDecoder.decode(link.attr("href"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("Error in  decoding the URL");
                e.printStackTrace();
            }

            final Matcher matcher = pattern.matcher(decodedUrl);

            while (matcher.find()) {

                Node node = Node.builder()
                        .rawMovieName(matcher.group(0))
                        .movieName(matcher.group(1).replace(".", " "))
                        .text(link.text())
                        .link(link.baseUri() + link.attr("href"))
                        .build();

                resultList.add(node);

            }

        }

//        for(Element link : links) {
//            System.out.println("After slash : " + link.attr("href"));
//            System.out.println("Text: " + link.text());
//            System.out.println("Full Link: " + link.baseUri() + link.attr("href"));
//
//            System.out.println("\n\n");
//        }


        return resultList;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        res += String.format(msg, args);
    }

}
