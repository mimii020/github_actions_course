package com.example.LunchMatch.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class InterestViewDTO {
    private Integer id;
    private String category;
    private String name;
    private String description;
}
