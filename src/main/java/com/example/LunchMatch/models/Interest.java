package com.example.LunchMatch.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Interest {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn
    private User user;
    private String category;
    private String name;
    private String description;
}
