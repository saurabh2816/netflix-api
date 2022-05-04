package com.javatechie.crud.netflix.repository;


import com.javatechie.crud.netflix.entity.ImdbMovieEntity;
import com.javatechie.crud.netflix.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<ImdbMovieEntity, Long> {

    Optional<ImdbMovieEntity> findByTitle(String title);
}
