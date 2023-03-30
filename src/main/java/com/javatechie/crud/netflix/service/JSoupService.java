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

        String url = "http://59.153.203.202/Data/All_Movies/hollywood/best/";

        String[] batches = new String[] {"Alien vs Predator (2004)",
                "Ant-Man (2015)",
                "Ant-Man And The Wasp (2018)",
                "Aquaman (2018)",
                "Avatar ECE (2009)",
                "Avengers Age of Ultron (2015)",
                "Avengers Endgame (2019)",
                "Avengers Infinity War (2018)",
                "Batman Begins (2005)",
                "Batman The Dark Knight (2008)",
                "Batman V Superman Dawn Of Justice (2016)",
                "Black Panther (2018)",
                "Bumblebee (2018)",
                "Captain America - The First Avenger (2011)",
                "Captain America Civil War (2016)",
                "Captain America The Winter Soldier (2014)",
                "Captain Marvel (2019)",
                "Dark Phoenix (2019)",
                "Despicable Me (2010)",
                "Despicable Me 2 (2013)",
                "Despicable Me 3 (2017)",
                "Die Hard (1988)",
                "Die Hard 2 (1990)",
                "Die Hard 3 (1995)",
                "Die Hard 4 (2007)",
                "Fantastic Beasts And Where To Find Them (2016)",
                "Fantastic Beasts The Crimes Of Grindelwald (2018)",
                "Fast & Furious Presents Hobbs & Shaw (2019)",
                "Fast Five (2011)",
                "Fast and Furious (2009)",
                "Furious 6 (2013)"};
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
