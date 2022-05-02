package com.javatechie.crud.netflix.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rating")
public class ImdbRatingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    private String source;
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    private ImdbRatingEntity rating;
}
