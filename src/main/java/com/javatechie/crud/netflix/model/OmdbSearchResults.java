package com.javatechie.crud.netflix.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OmdbSearchResults {

    @JsonProperty("Search")
    private List<OmdbSearchResult> search;
    private int totalResults;
    boolean response;

}
