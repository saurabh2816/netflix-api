package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.exception.NetflixException;
import com.javatechie.crud.netflix.model.Movie;
import com.javatechie.crud.netflix.model.OmdbSearchResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OmdbAPIService {

    private static final String API_KEY = "cd04853d";
    String url = "http://www.omdbapi.com/";
    WebClient webClient = WebClient.create(url);

    public OmdbSearchResults getMovieDetails(String query) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("s", query)
                        .build())
                .retrieve()
                .onStatus(status -> status.value() >= HttpStatus.BAD_REQUEST.value(), clientResponse ->
                        Mono.error(new NetflixException(clientResponse.statusCode(), "Request failed from getMovieDetails()")))
                .bodyToMono(new ParameterizedTypeReference<OmdbSearchResults>() {
                })
                .block();

    }

    public Movie getMoviesByQuery(String query) {

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
