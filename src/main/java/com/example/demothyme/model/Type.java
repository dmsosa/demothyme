package com.example.demothyme.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public enum Type {
    ELECTRIC("ELECTRIC"),
    WATER("WATER"),
    FIRE("FIRE"),
    GRASS("GRASS"),
    DRAGON("DRAGON"),
    ROCK("ROCK"),

    FLYING("FLYING"),
    POISON("POISON"),
    PSYCHIC("PSYCHIC"),
    DARK("DARK"),
    FAIRY("FAIRY"),
    STEEL("STEEL"),
    ICE("ICE"),
    NORMAL("NORMAL"),
    FIGHTING("FIGHTING"),
    BUG("BUG"),
    GHOST("GHOST"),
    GROUND("GROUND")
    ;

    public static final Type[] ALL = { ELECTRIC, WATER, FIRE, GRASS,
            DRAGON, ROCK, FLYING, POISON,
            PSYCHIC, DARK, FAIRY, STEEL,
            ICE, NORMAL, FIGHTING, BUG,
            GHOST, GROUND };
    private final String name;

    public static Type forName(String name) {
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
            case "GRASS" : {
                return GRASS;
            }
            case "DRAGON" : {
                return DRAGON;
            }
            case "ROCK" : {
                return ROCK;
            }
            case "FLYING" : {
                return FLYING;
            }
            case "POISON" : {
                return POISON;
            }
            case "PSYCHIC" : {
                return PSYCHIC;
            }
            case "DARK" : {
                return DARK;
            }
            case "FAIRY" : {
                return FAIRY;
            }
            case "STEEL" : {
                return STEEL;
            }
            case "ICE" : {
                return ICE;
            }
            case "NORMAL" : {
                return NORMAL;
            }
            case "FIGHTING" : {
                return FIGHTING;
            }
            case "BUG" : {
                return BUG;
            }
            case "GHOST" : {
                return GHOST;
            }
            case "GROUND" : {
                return GROUND;
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
