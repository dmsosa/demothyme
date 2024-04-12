package com.example.demothyme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "trainer")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phrase;
    private String picture;
    @Enumerated(value = EnumType.STRING)
    private List<Badge> badges;
    @OneToMany(mappedBy = "trainer", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Pokemon> team = new ArrayList<Pokemon>();
}
