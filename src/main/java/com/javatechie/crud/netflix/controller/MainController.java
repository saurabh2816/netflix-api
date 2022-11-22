package com.javatechie.crud.netflix.controller;
import com.javatechie.crud.netflix.entity.Movie;
import com.javatechie.crud.netflix.model.ImdbMovie;
import com.javatechie.crud.netflix.model.OmdbSearchResults;
import com.javatechie.crud.netflix.service.MovieService;
import com.javatechie.crud.netflix.service.OmdbAPIService;

import com.javatechie.crud.netflix.model.Node;
import com.javatechie.crud.netflix.service.JSoupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(maxAge = 3600, allowedHeaders = "*" )
@RequestMapping(path = "/api/v1")
public class MainController {



    @Autowired
    private final JSoupService JSoupService;

    private final OmdbAPIService omdbAPIService;
    private final MovieService movieService;
    private Map<String, String> myMap;

    public MainController(JSoupService JSoupService, OmdbAPIService omdbAPIService, MovieService movieService) {
        this.JSoupService = JSoupService;
        this.omdbAPIService = omdbAPIService;
        this.movieService = movieService;
    }

    @GetMapping("/saveAllMoviesFromALink")
    public ResponseEntity<List<Movie>> getListOfNodesFromOneLink() {

        List<Node> res = JSoupService.getListOfNodesFromLink();

        List<Movie> movieList = new ArrayList<>();
        for (Node node : res) {
            if (node.getRawMovieName().contains(".mp4") && !node.getMovieName().isEmpty()) { // TODO: Add support for more media types

                Movie movie = omdbAPIService.getMovieByTitle(node);


                // whuy would imdb Id be null? => maybe we don't have info for but that should be handled before inside the getMovieByTitle()
                if (movie.getImdbId() != null) {
                    movieList.add(movie);
                } else {
                    log.error("This node has some issue. Debug node to find out. New format hai boss!!");
                }
            }
        }

        return ResponseEntity.ok(movieList);

    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Movie>> getAllMovies() {

        List<Movie> res = movieService.getAllMovies();
        return ResponseEntity.ok(res);

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


//    @GetMapping("/movies/bytitle/{title}")
//    public ImdbMovieEntity getMovieByTitle(@PathVariable String title) {
//
//        return omdbAPIService.getMovieByTitle(title);
//
//    }





    // Testing endpoint
    @GetMapping("/fun")
    public ResponseEntity<String> newendpoint() {

        String res = "saurabh and ronak";
        return ResponseEntity.ok(res);

    }
}
