CREATE TABLE trainer (
        id INTEGER NOT NULL AUTO_INCREMENT,
        name VARCHAR(40) UNIQUE,
        phrase VARCHAR (100),
        picture TEXT,
        badges ENUM('BUG', 'ROCK', 'FIGHT', 'GRASS', 'ELECTRIC', 'FAIRY', 'PSYCHIC', 'ICE') ARRAY,
        PRIMARY KEY (id)
);

CREATE TABLE pokemon (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    type ENUM('ELECTRIC', 'WATER', 'FIRE', 'GRASS', 'DRAGON', 'ROCK', 'FLYING', 'POISON',
    'PSYCHIC', 'DARK', 'FAIRY', 'STEEL', 'ICE',
    'NORMAL', 'FIGHTING', 'BUG', 'GHOST', 'GROUND') ARRAY,
    trainer_name VARCHAR(40),
    ability VARCHAR(20),
    description VARCHAR(500),
    picture TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (trainer_name) REFERENCES trainer (name)
);