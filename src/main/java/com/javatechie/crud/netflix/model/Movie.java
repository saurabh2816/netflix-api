package com.javatechie.crud.netflix.model;

import com.javatechie.crud.netflix.entity.ImdbMovieEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private ImdbMovieEntity info;
    private String url;
}