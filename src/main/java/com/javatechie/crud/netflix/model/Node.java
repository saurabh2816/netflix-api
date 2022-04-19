package com.javatechie.crud.netflix.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    private String movieName;
    private String rawMovieName;
    private String text;
    private String link;

}
