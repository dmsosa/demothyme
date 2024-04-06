CREATE TABLE trainer (
        name VARCHAR(40) NOT NULL UNIQUE PRIMARY KEY,
        picture TEXT,
        badges ENUM('BUG', 'ROCK', 'FIGHT', 'GRASS', 'ELECTRIC', 'FAIRY', 'PSYCHIC', 'ICE') ARRAY
);

CREATE TABLE pokemon (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    type ENUM('ELECTRIC', 'WATER', 'FIRE', 'GRASS', 'DRAGON', 'ROCK'),
    trainer_name VARCHAR(40) NOT NULL,
    ability VARCHAR(20),
    description VARCHAR(500),
    picture TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (trainer_name) REFERENCES trainer (name)
);