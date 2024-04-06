package com.example.demothyme.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public enum Type {
    ELECTRIC("ELECTRIC"),
    WATER("WATER"),
    FIRE("FIRE"),
    DRAGON("DRAGON"),
    ROCK("ROCK"),
    GRASS("GRASS");

    public static final Type[] ALL = { ELECTRIC, WATER, FIRE, DRAGON, ROCK,  GRASS };
    private final String name;

    private static Type forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        String upperCaseName = name.toUpperCase();
        switch ( upperCaseName ) {
            case "ELECTRIC" : {
                return ELECTRIC;
            }
            case "WATER" : {
                return WATER;
            }
            case "FIRE" : {
                return FIRE;
            }
            case "DRAGON" : {
                return DRAGON;
            }
            case "ROCK" : {
                return ROCK;
            }
            case "GRASS" : {
                return GRASS;
            }
            default: {
                throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any Type");
            }
        }
    }
    private Type(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() {
        return this.getName();
    }
}
