package com.javatechie.crud.netflix.controller;
import com.javatechie.crud.netflix.model.ImdbMovie;
import com.javatechie.crud.netflix.model.Movie;
import com.javatechie.crud.netflix.model.OmdbSearchResults;
import com.javatechie.crud.netflix.service.OmdbAPIService;

import com.javatechie.crud.netflix.model.Node;
import com.javatechie.crud.netflix.service.JSoupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "/api/v1")
public class MainController {



    @Autowired
    private JSoupService JSoupService;
    private OmdbAPIService omdbAPIService;
    private Map<String, String> myMap;

    public MainController(JSoupService JSoupService, OmdbAPIService omdbAPIService) {
        this.JSoupService = JSoupService;
        this.omdbAPIService = omdbAPIService;
    }

    @GetMapping("/getOne")
    public ResponseEntity<List<Movie>> getListOfNodesFromOneLink() {

        List<Node> res = JSoupService.getListOfNodesFromLink();

        List<Movie> movieList = new ArrayList<>();
        for (Node node : res) {
            if(node.getRawMovieName().contains(".mp4")) { // TODO: Add support for more media types
                ImdbMovie imdbMovie = omdbAPIService.getMovieByTitle(node.getMovieName());
                movieList.add(Movie.builder()
                        .info(imdbMovie)
                        .url(node.getLink())
                        .build());
            }
        }

//        res.stream().filter(node -> !node.getRawMovieName().contains(".srt")).map(node -> {
//            Movie movie = omdbAPIService.getMovieByTitle(node.getMovieName());
//            return movieList.add(movie);
//        });

        return ResponseEntity.ok(movieList);

    }


    @GetMapping("/movies/{id}")
    public ImdbMovie getMovieById(@PathVariable String id) {

        return omdbAPIService.getMovieById(id);

    }

    @GetMapping("/cache")
    public String getFromCache() {
        return  myMap.get("Hello");
    }


    // TODO: use this API to somehow provide realtime search
    @GetMapping("/movies/search/{query}")
    public OmdbSearchResults getMoviesByQuery(@PathVariable String query) {

        return omdbAPIService.getMoviesByQuery(query);

    }


    @GetMapping("/movies/bytitle/{title}")
    public ImdbMovie getMovieByTitle(@PathVariable String title) {

        return omdbAPIService.getMovieByTitle(title);

    }





    // Testing endpoint
    @GetMapping("/fun")
    public ResponseEntity<String> newendpoint() {

        String res = "saurabh and ronak";
        return ResponseEntity.ok(res);

    }
}
