package com.watch_anime_v2.anime;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "anime_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ElementCollection
    private List<String> genre;  // массив жанров

    private String format;
    private String status;
    private String episodes;
    private String studio;
    private String startDate;
}
