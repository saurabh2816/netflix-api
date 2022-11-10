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

        String url = "http://167.114.174.132:9092/movies/";

        String[] batches = new String[] {"Batch235/" };
//        String[] batches = new String[] {"Batch212/"};
        List<Node> resultList = new ArrayList<>();

        for(String batchName: batches) {

            Document doc = null;
            try {
                doc = Jsoup.connect(url + batchName).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements links = doc.select("a[href]");


            // REGEX
            final String regex = "([\\.\\w']+?)(\\.[0-9]{4}\\..*)";
            final Pattern pattern = Pattern.compile(regex);

            for (Element link : links) {

                String decodedUrl = "";

                // decode the href URL because it was failing for  Bridget.Jones%27s.Baby.2016.720p.BluRay.x264-%5BYTS.AG%5D.mp4
                try {
                    decodedUrl = URLDecoder.decode(link.attr("href"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    log.error("Error in  decoding the URL");
                    e.printStackTrace();
                }

                final Matcher matcher = pattern.matcher(decodedUrl);

                while (matcher.find()) {

                    String rawFileName = matcher.group(0);
                    String movieName = matcher.group(1).replace(".", " ");
                    // SRT FILE
                    if(rawFileName.contains("srt")) {

                        // this is a srt file and needs to be associated with the previous mp4 movie entry in the resultList
                        if(resultList.get(resultList.size()-1).getMovieName().equals(movieName)) {

                            // this subtitle belongs to this previous movie
                            Node previousMovie = resultList.get(resultList.size()-1);
                            previousMovie.setStrLink(link.baseUri() + link.attr("href"));

                        }
                    }

                    // ALLEGED MP4 FILE
                    else {

                        Node node = Node.builder()
                                .rawMovieName(matcher.group(0))
                                .movieName(movieName)
                                .text(link.text())
                                .link(link.baseUri() + link.attr("href"))
                                .build();

                        resultList.add(node);
                    }
                }

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
