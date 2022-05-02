package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.entity.ImdbMovieEntity;
import com.javatechie.crud.netflix.exception.NetflixException;
import com.javatechie.crud.netflix.model.ImdbMovie;
import com.javatechie.crud.netflix.model.OmdbSearchResults;
import com.javatechie.crud.netflix.repository.MovieRepository;
import com.javatechie.crud.netflix.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class OmdbAPIService {

    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;

    public OmdbAPIService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    private static final String API_KEY = "cd04853d";
    String url = "http://www.omdbapi.com/";
    WebClient webClient = WebClient.create(url);

    public OmdbSearchResults getMoviesByQuery(String query) {

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

    public ImdbMovieEntity getMovieByTitle(String title) {

        // TODO: check if the movie is present in DB
        Optional<ImdbMovieEntity> movie = movieRepository.findByTitle(title);
//
        if(movie.isPresent()) {

            return movie.get();

//            return ImdbMovie.builder()
//                    .title(movie.get().getTitle())
//                    .year(movie.get().getYear())
//                    .rated(movie.get().getRated())
//                    .released(movie.get().getReleased())
//                    .runtime(movie.get().getRuntime())
//                    .genre(movie.get().getGenre())
//                    .director(movie.get().getDirector())
//                    .writer(movie.get().getWriter())
//                    .actors(movie.get().getActors())
//                    .plot(movie.get().getPlot())
//                    .language(movie.get().getLanguage())
//                    .country(movie.get().getCountry())
//                    .awards(movie.get().getAwards())
//                    .poster(movie.get().getPoster())
//                    .ratings(movie.get().getRatings())
//                    .metascore(movie.get().getMetascore())
//                    .imdbRating(movie.get().getImdbRating())
//                    .imdbVotes(movie.get().getImdbVotes())
//                    .imdbId(movie.get().getImdbId())
//                    .type(movie.get().getType())
//                    .dvd(movie.get().getDvd())
//                    .boxOffice(movie.get().getBoxOffice())
//                    .production(movie.get().getProduction())
//                    .website(movie.get().getWebsite())
//                    .response(movie.get().getResponse())
//                    .build();
        }

        // TODO: if not make an IMDB call and save to db

        ImdbMovie res = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("t", title)
                        .build())
                .retrieve()
                .bodyToMono(ImdbMovie.class)
                .block();

        // save res to the movie table
        ImdbMovieEntity movieEntity= movieRepository.save(ImdbMovieEntity.builder()
                        .actors(res.getActors())
                        .awards(res.getAwards())
                        .boxOffice(res.getBoxOffice())
                        .country(res.getCountry())
                        .director(res.getDirector())
                        .dvd(res.getDvd())
                        .genre(res.getGenre())
                        .runtime(res.getRuntime())
                        .imdbId(res.getImdbId())
                        .imdbRating(res.getImdbRating())
                        .imdbVotes(res.getImdbVotes())
                        .language(res.getLanguage())
                        .metascore(res.getMetascore())
                        .plot(res.getPlot())
                        .poster(res.getPoster())
                        .production(res.getProduction())
                        .rated(res.getRated())
//                        .ratings(res.getRatings())
                        .response(res.getResponse())
                        .type(res.getType())
                        .website(res.getWebsite())
                        .writer(res.getWriter())
                        .title(res.getTitle())
                        .released(res.getReleased())
                        .year(res.getYear())
                        .released(res.getReleased())
                .build());

        return movieEntity;

    }




    public ImdbMovie getMovieById(@PathVariable String id) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("i", id)
                        .build())
                .retrieve()
                .bodyToMono(ImdbMovie.class)
                .block();

    }
}
