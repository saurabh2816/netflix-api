package com.javatechie.crud.netflix.controller;
import com.javatechie.crud.netflix.model.Movie;
import com.javatechie.crud.netflix.service.OmdbAPIService;
import org.springframework.web.reactive.function.client.WebClient;

import com.javatechie.crud.netflix.model.Node;
import com.javatechie.crud.netflix.service.JSoupService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
public class MainController {



    @Autowired
    private JSoupService JSoupService;
    private OmdbAPIService omdbAPIService;

    public MainController(JSoupService JSoupService, OmdbAPIService omdbAPIService) {
        this.JSoupService = JSoupService;
        this.omdbAPIService = omdbAPIService;
    }

    @GetMapping("/getOne")
    public ResponseEntity<List<Node>> getListOfNodesFromOneLink() {

        List<Node> res = JSoupService.getListOfNodesFromLink();
        return ResponseEntity.ok(res);

    }


    @GetMapping("/movies/{id}")
    public Movie getMovieById(@PathVariable String id) {

        return omdbAPIService.getMovieById(id);

    }

    @GetMapping("/movies/byname/{query}")
    public Movie getMovieDetails(@PathVariable String query) {

        return omdbAPIService.getMovieDetails(query);

    }





    // Testing endpoint
    @GetMapping("/fun")
    public ResponseEntity<String> newendpoint() {

        String res = "saurabh and ronak";
        return ResponseEntity.ok(res);

    }
}
