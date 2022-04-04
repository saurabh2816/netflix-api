package com.javatechie.crud.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class NetflixApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixApplication.class, args);

		//        System.out.println("saurabh");
//
        // JSOUP
//        String url = "http://167.114.174.132:9092/movies/Batch212/";
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(url).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Elements links = doc.select("a[href]");
//
//        // REGEX
//        final String regex = "([\\.\\w']+?)(\\.[0-9]{4}\\..*)";
//        final Pattern pattern = Pattern.compile(regex);
//
//
//        print("\nLinks: (%d)", links.size());
//        for (Element link : links) {
//
//            final String LINK = link.attr("abs:href");
//            String result = java.net.URLDecoder.decode(LINK, StandardCharsets.UTF_8);
//
//            print("\n\n  * a:  (%s)", LINK);
//
//            final Matcher matcher = pattern.matcher(link.attr("abs:href"));
//
//            while (matcher.find()) {
//                System.out.println("Full match: " + matcher.group(0));
//
//                for (int i = 1; i <= matcher.groupCount(); i++) {
//                    System.out.println("Group " + i + ": " + matcher.group(i));
//                }
//            }
//
//        }
//        // TODO: use JSOUP to get a list of all the List<String> urls;
//        /*
//
//        /movies
//        GET: list of all the movies stores in the database
//
//        - return an a format to generate the home page
//
//        /openindexsearch/:link
//        GET: all the movies from a link with metadata as well
//        -----------------------------------------------------
//         [] Get all links from the link in a List<String> links using JSOUP
//         [] TRY to extract movie_name (decode if necessary), quality, Print, audio/video encoding, lanaguage etc more https://github.com/divijbindlish/parse-torrent-name/blob/master/PTN/patterns.py)
//             [] map the above object to a class say Movie (i.e model)
//             [] List<Movie> needs to be queried for information from IMDB API
//             [] Information returned needs to be saved into database as API calls are limited to 1000.
//                - Design SQL tables according the response. Don't focus on creating relationships because we can't be sure.
//                - Save some data in JSON
//
//        UI (/openindexsearch)
//        ---------------------
//        HomeComponent (/movies)
//            &
//        Three functional components like Netflix => just the image -> plays youtube trailer with metadata -> plays video in full screen
//        1. movieComponent: just the image of the movie in a card and on hover load movieCardComponent
//        2. movieCardComponent: contains most of the metadata from IMDB and a youtube trailer if possible or just an image
//        3. moviePlayer: custom angular player to play the video
//
//
//
//         */
//
//
//    }


	}

//    private static void print(String msg, Object... args) {
//        System.out.println(String.format(msg, args));
//    }

}
