package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class OmdbAPIService {

    private static final String API_KEY = "cd04853d";
    String url = "http://www.omdbapi.com/";
    WebClient webClient = WebClient.create(url);

    public Movie getMovieDetails(String query) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("t", query)
                        .build())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();

    }

    public Movie getMovieById(@PathVariable String id) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("i", id)
                        .build())
                .retrieve()
                .bodyToMono(Movie.class)
                .block();

    }
}
