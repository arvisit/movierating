/*
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
 */

CREATE TABLE IF NOT EXISTS roles (
    id SMALLSERIAL PRIMARY KEY,
    "name" VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    login VARCHAR(100) NOT NULL,
    "password" VARCHAR(32) NOT NULL,
    role_id SMALLINT NOT NULL REFERENCES roles,
    info TEXT NOT NULL DEFAULT '',
    reputation SMALLINT NOT NULL DEFAULT 0,
    registration TIMESTAMPTZ(6) NOT NULL DEFAULT NOW(),
    last_update TIMESTAMPTZ(6) NOT NULL DEFAULT NOW(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO roles ("name")
VALUES ('ADMIN'),
    ('USER');

INSERT INTO users (email, login, "password", role_id, info)
VALUES ('user@mail.com', 'user', '1234', (SELECT id FROM roles WHERE name = 'USER'), 'I am a test user'),
    ('admin@mail.com', 'admin', '4321', (SELECT id FROM roles WHERE name = 'ADMIN'), 'I am an administrator here'),
    ('heath.good@live.com', 'heath_good', 'n2sjwmnMlDsOkXid4140m0a3179dPOUq', (SELECT id FROM roles WHERE name = 'USER'), 'I like movies'),
    ('elaina.vaughan@hotmail.de', 'elaina_vaughan', 'MNBe7NqF1xEAq5oM1EYyDpZVhuiJXTt3', (SELECT id FROM roles WHERE name = 'USER'), 'I like TV-series'),
    ('augustine.wyatt@live.nl', 'augustine_wyatt', 'xGWVQaT2SkOnhNfGSyHipJtbnkjKOGwS', (SELECT id FROM roles WHERE name = 'USER'), 'I am a troll'),
    ('neo.shelton@sky.com', 'neo_shelton', '8MfKysE7ulTqo1nO3qDtoCM7bMmPuYU9', (SELECT id FROM roles WHERE name = 'USER'), 'I am a potat'),
    ('ryder.nolan@rambler.ru', 'ryder_nolan', '2w3Kkf0OBduGucu6LXpK6lEorgCADhvJ', (SELECT id FROM roles WHERE name = 'USER'), 'Just do it'),
    ('alice.le@yahoo.com.sg', 'alice_le', 'pratnISWj6giyKU99Y3h86CP7f5xKZjv', (SELECT id FROM roles WHERE name = 'USER'), 'Mai Inglish iz very bed'),
    ('carly.holt@cox.net', 'carly_holt', 'aL04gk3e6I8CMmfnIBJQp4Z2t92efAGK', (SELECT id FROM roles WHERE name = 'USER'), 'Older does not meen old'),
    ('hayden.gray@optusnet.com.au', 'hayden_gray', 'JzPEn7uhyPuEdX477S30u18upB5o5vxI', (SELECT id FROM roles WHERE name = 'USER'), ''),
    ('byron.tapia@yahoo.com.au', 'byron_tapia', 'Y2Qfpbg8IblJChIGE4anr3r2IvWpC91T', (SELECT id FROM roles WHERE name = 'USER'), 'I have nothing to write here'),
    ('blake.booker@mac.com', 'blake_booker', 'T0oSQ2aIJ2GQ961S5qQrs00Xnyv0RfXA', (SELECT id FROM roles WHERE name = 'USER'), 'Hello world'),
    ('jacob.sweeney@yahoo.de', 'jacob_sweeney', 'Ms0OG74wTjtqEnh6rdJ4wtEBnQbnSjjZ', (SELECT id FROM roles WHERE name = 'USER'), ''),
    ('alaric.gentry@rambler.ru', 'alaric_gentry', 'VIuBX1BDJ1gKv2lSfoVp5OkHQP6lnlXf', (SELECT id FROM roles WHERE name = 'USER'), 'Does it mean anything to you'),
    ('jericho.conway@yahoo.in', 'jericho_conway', 'oRDSi0gaW2A3Za1X4bTpD9zkoL3GEbdr', (SELECT id FROM roles WHERE name = 'USER'), 'I like Papa Roach'),
    ('evelyn.daugherty@hotmail.de', 'evelyn_daugherty', 'EiU75ASivkskmENdcTRX0BJtPmj4JdUR', (SELECT id FROM roles WHERE name = 'USER'), 'In the Flying Spaghetti Monster we trust'),
    ('mathew.coleman@juno.com', 'mathew_coleman', 'FVuxFonjsqmlZB5hoSygmRtl0kI9XPY7', (SELECT id FROM roles WHERE name = 'USER'), ''),
    ('leila.mason@sympatico.ca', 'leila_mason', 'fLeVxqTXfY9NYETnjIYd0fKK73YsWK6T', (SELECT id FROM roles WHERE name = 'USER'), 'To serve and to protect'),
    ('brooke.morton@live.com.au', 'brooke_morton', 'LsIi0cNPXGNATHEXFtawyBsUKvkMq8Yb', (SELECT id FROM roles WHERE name = 'USER'), 'WAAAGH!'),
    ('estelle.weeks@yahoo.in', 'estelle_weeks', 'Y5p40rdp7aiRuidK4omzjp0UilrIXiA2', (SELECT id FROM roles WHERE name = 'USER'), 'Make love not Warcraft'),
    ('jayla.ochoa@arcor.de', 'jayla_ochoa', 'PoujDILBgaiSJsSWw5obwJmH1xpjJl09', (SELECT id FROM roles WHERE name = 'USER'), 'Can not see a thing'),
    ('aidan.berg@bol.com.br', 'aidan_berg', 'rcF2dxaSMbDj7sXTIstceFlGLkvv02fI', (SELECT id FROM roles WHERE name = 'USER'), 'I am good at blinddate. I am Daredevil');
