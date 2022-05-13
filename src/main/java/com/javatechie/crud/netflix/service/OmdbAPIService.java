package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.entity.Movie;
import com.javatechie.crud.netflix.exception.NetflixException;
import com.javatechie.crud.netflix.model.ImdbMovie;
import com.javatechie.crud.netflix.model.Node;
import com.javatechie.crud.netflix.model.OmdbSearchResults;
import com.javatechie.crud.netflix.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class OmdbAPIService {

    private MovieRepository movieRepository;

    public OmdbAPIService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    private static final String API_KEY = "cd04853d";
    String url = "http://www.omdbapi.com/";
    WebClient webClient = WebClient.create(url);
    WebClient posterAvailabilityCheckClient = WebClient.create();

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


    private Mono<Throwable> handleErrors(ClientResponse response ) {
        return response.bodyToMono(String.class).flatMap(body -> {
            log.error("LOg errror");
            return Mono.error(new Exception());
        });
    }

    public Movie getMovieByTitle(Node node) {

        String title = node.getMovieName();

        Optional<Movie> movie = movieRepository.findByTitle(title);

        if(movie.isPresent()) {
            return movie.get();
        }

        ImdbMovie res = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", API_KEY)
                        .queryParam("t", title)
                        .build())
                .retrieve()
                .bodyToMono(ImdbMovie.class)
                .block();

        // check for poster availability before saving

//        Mono<String> monoHttpStatus = posterAvailabilityCheckClient.get()
//                .uri(res.getPoster())
//                .exchange()
//                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class));

//        Mono<Void> status = posterAvailabilityCheckClient.get()
//                .uri(res.getPoster())
//                .retrieve()
//                .onStatus(HttpStatus::isError, response ->  Mono.error(new NetflixException(HttpStatus.INTERNAL_SERVER_ERROR, "sfgdfg")))
//                .bodyToMono(Void.class);


//        Mono<ClientResponse> clientResponse = WebClient.builder().build()
//                .get().uri(res.getPoster())
//                .exchange();
//
//        HttpStatus scode;
//        clientResponse.subscribe((response) -> {
//
//            HttpStatus statusCode = response.statusCode();
//
//            Mono<String> bodyToMono = response.bodyToMono(String.class);
//            // the second subscribe to access the body
//            bodyToMono.subscribe((body) -> {
//
//                System.out.println("stausCode:" + statusCode);
//
//            }, (ex) -> {
//                // handle error
//            });
//        }, (ex) -> {
//            // handle network error
//        });


        // res could be null if there doesn't exist a movie with that name
        // use cases include: movie title missing apostrophes, error in extracting the movie name from the link etc.

        if(res.getTitle()==null) return Movie.builder().build();

            // save res to the movie table
            Movie movieEntity = movieRepository.save(Movie.builder()
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
//                  .ratings(res.getRatings())  -- we are not saving ratings
                    .response(res.getResponse())
                    .type(res.getType())
                    .website(res.getWebsite())
                    .writer(res.getWriter())
                    .title(res.getTitle())
                    .released(res.getReleased())
                    .year(res.getYear())
                    .released(res.getReleased())

                    // data from node movielink and srtLink
                    .link(node.getLink())
                    .srtLink(node.getStrLink())

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
