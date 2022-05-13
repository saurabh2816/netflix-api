package com.javatechie.crud.netflix.service;

import com.javatechie.crud.netflix.entity.Movie;
import com.javatechie.crud.netflix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }


}
