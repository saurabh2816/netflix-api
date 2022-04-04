package com.javatechie.crud.netflix.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestingService {

    private static String res;

    public String something() {

        String url = "http://167.114.174.132:9092/movies/Batch212/";

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


        print("\nLinks: (%d)", links.size());
        for (Element link : links) {

            final String LINK = link.attr("abs:href");
            String result = java.net.URLDecoder.decode(LINK, StandardCharsets.UTF_8);

            print("\n\n  * a:  (%s)", LINK);

            final Matcher matcher = pattern.matcher(link.attr("abs:href"));

            while (matcher.find()) {
                System.out.println("Full match: " + matcher.group(0));

                for (int i = 1; i <= matcher.groupCount(); i++) {
                    System.out.println("Group " + i + ": " + matcher.group(i));
                }
            }

        }

        return res;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
        res += String.format(msg, args);
    }

}
