CREATE TABLE IF NOT EXISTS USERS
(
    userid    SERIAL PRIMARY KEY,
    username  VARCHAR(20),
    salt      VARCHAR,
    password  VARCHAR,
    firstname VARCHAR(20),
    lastname  VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES
(
    noteid          SERIAL PRIMARY KEY,
    notetitle       VARCHAR(20),
    notedescription VARCHAR(1000),
    userid          INT,
    FOREIGN KEY (userid) REFERENCES USERS (userid)
);

CREATE TABLE IF NOT EXISTS FILES
(
    fileId      SERIAL PRIMARY KEY,
    filename    VARCHAR, -- No need to specify size for VARCHAR in PostgreSQL
    contenttype VARCHAR, -- No need to specify size for VARCHAR in PostgreSQL
    filesize    VARCHAR, -- No need to specify size for VARCHAR in PostgreSQL
    userid      INT,
    filedata    BYTEA,   -- Use BYTEA for storing binary data in PostgreSQL
    FOREIGN KEY (userid) REFERENCES USERS (userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS
(
    credentialid SERIAL PRIMARY KEY,
    url          VARCHAR(100),
    username     VARCHAR(30),
    key          VARCHAR, -- No need to specify size for VARCHAR in PostgreSQL
    password     VARCHAR, -- No need to specify size for VARCHAR in PostgreSQL
    userid       INT,
    FOREIGN KEY (userid) REFERENCES USERS (userid)
);