CREATE TABLE IF NOT EXISTS  shedlock (
    name        VARCHAR(200) NOT NULL,
    lock_until  TIMESTAMP NOT NULL,
    locked_at   TIMESTAMP NOT NULL,
    locked_by   VARCHAR(200) NOT NULL,
    PRIMARY KEY (name)
);