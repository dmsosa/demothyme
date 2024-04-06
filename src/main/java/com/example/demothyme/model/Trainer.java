package com.example.demothyme.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;
    private String picture;
    private List<Badge> badges;
    @OneToMany(mappedBy = "trainer")
    private Set<Pokemon> team;
}
