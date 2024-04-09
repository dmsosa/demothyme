
package com.example.demothyme.model;

public enum Badge {
    BUG("BUG"), ROCK("ROCK"), FIGHT("FIGHT"), GRASS("GRASS"),
    ELECTRIC("ELECTRIC"), FAIRY("FAIRY"), PSYCHIC("PSYCHIC"), ICE("ICE");

    public static Badge[] ALL = {BUG, ROCK, FIGHT, GRASS, ELECTRIC, FAIRY, PSYCHIC, ICE};

    private String name;
    private Badge(String name) {
        this.name = name;
    }
    public static Badge forName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        String upperCaseName = name.toUpperCase();
        switch ( upperCaseName ) {
            case "BUG" : {
                return BUG;
            }
            case "ROCK" : {
                return ROCK;
            }
            case "FIGHT" : {
                return FIGHT;
            }
            case "GRASS" : {
                return GRASS;
            }
            case "ELECTRIC" : {
                return ELECTRIC;
            }
            case "FAIRY" : {
                return FAIRY;
            }
            case "PSYCHIC" : {
                return PSYCHIC;
            }
            case "ICE" : {
                return ICE;
            }
            default: {
                throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any Badge");
            }
        }
    }
    public String getName() {
        return this.name();
    }
}
