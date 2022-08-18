/*
DROP TABLE IF EXISTS bans;
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
    "password" VARCHAR(40) NOT NULL,
    role_id SMALLINT NOT NULL REFERENCES roles(id),
    info TEXT NOT NULL DEFAULT '',
    reputation SMALLINT NOT NULL DEFAULT 0,
    registration TIMESTAMPTZ(3)NOT NULL DEFAULT NOW(),
    last_update TIMESTAMPTZ(3) NOT NULL DEFAULT NOW(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS bans (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    admin_id BIGINT NOT NULL REFERENCES users(id),
    start_date TIMESTAMPTZ(3) NOT NULL,
    end_date TIMESTAMPTZ(3) NOT NULL,
    reason TEXT NOT NULL,
    last_update TIMESTAMPTZ(3) NOT NULL DEFAULT NOW(),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO roles ("name")
VALUES ('ADMIN'),
    ('USER');

INSERT INTO users (email, login, "password", role_id, info)
VALUES ('user@mail.com', 'user', '7110EDA4D09E062AA5E4A390B0A572AC0D2C0220', (SELECT id FROM roles WHERE name = 'USER'), 'I am a test user'),
    ('admin@mail.com', 'admin', 'D5F12E53A182C062B6BF30C1445153FAFF12269A', (SELECT id FROM roles WHERE name = 'ADMIN'), 'I am an administrator here'),
    ('sawyer.gates@msn.com', 'sawyer_gates', 'E89D7BC02A6F6B7948273B94525B407595D05627', (SELECT id FROM roles WHERE name = 'USER'), 'I find the best way to love someone is not to change them, but instead, help them reveal the greatest version of themselves.'),
    ('adalynn.bravo@sfr.fr', 'adalynn_bravo', 'BD4033A8D4223E75141F3389FE1DC032505D5F12', (SELECT id FROM roles WHERE name = 'USER'), 'Be yourself, don�t take anything from anyone, and never let them take you alive.'),
    ('nova.christian@bluewin.ch', 'nova_christian', 'B7CE17B4E57C415238C44576D2CEAEA4E2A18B17', (SELECT id FROM roles WHERE name = 'USER'), 'When someone is properly grounded in life, they shouldn�t have to look outside themselves for approval.'),
    ('hadley.sampson@telenet.be', 'hadley_sampson', 'CB4614D2D7EAB0C7DABC7BB511454DEFC3B7AC3D', (SELECT id FROM roles WHERE name = 'USER'), 'It is better to be hated for what you are than to be loved for something you are not.'),
    ('benedict.brown@neuf.fr', 'benedict_brown', '3BD126F1C8D966A9B04E9B7F7652CFB13152B4EC', (SELECT id FROM roles WHERE name = 'USER'), 'A dead thing can go with the stream, but only a living thing can go against it.'),
    ('leighton.pe?a@tiscali.co.uk', 'leighton_pe?a', '58239F6D45786B64B7D0192498F81516EA217B2E', (SELECT id FROM roles WHERE name = 'USER'), 'To be yourself in a world that is constantly trying to make you something else is the greatest accomplishment.'),
    ('austyn.good@live.fr', 'austyn_good', 'F49DB94D31A4E2EA02483CFBEAB5F09662A241E4', (SELECT id FROM roles WHERE name = 'USER'), 'Be yourself is about the worst advice you can give to some people.'),
    ('skylar.gardner@t-online.de', 'skylar_gardner', '67223CD7ABA79793E9DD2FDF03D69512F41F5893', (SELECT id FROM roles WHERE name = 'USER'), 'You change the world by being yourself.'),
    ('zainab.stewart@live.co.uk', 'zainab_stewart', '56A7DED8D1445111DEA88E614BE400E9E6B9166A', (SELECT id FROM roles WHERE name = 'USER'), 'The individual has always had to struggle to keep from being overwhelmed by the tribe. If you try it, you will be lonely often, and sometimes frightened. But no price is too high to pay for the privilege of owning yourself.'),
    ('jaxtyn.garrett@libero.it', 'jaxtyn_garrett', 'CA785E982DB390200281FD267C998851A6CF5F69', (SELECT id FROM roles WHERE name = 'USER'), 'If you can just be yourself, then you have to be original because there�s no one like you.'),
    ('kensley.rogers@charter.net', 'kensley_rogers', '9291A38F2D38FDC420EEA58B928B912D988BBFCD', (SELECT id FROM roles WHERE name = 'USER'), 'Don�t try to impress people. Always be yourself.'),
    ('hadlee.rosario@yahoo.co.in', 'hadlee_rosario', '230B9A2998468ECBF2A2EB63256DD8683D31E37C', (SELECT id FROM roles WHERE name = 'USER'), 'Where�s your will to be weird?'),
    ('talon.carlson@comcast.net', 'talon_carlson', '85CEDA82CF9D673B2478FC2064697BE9FF9CA6E1', (SELECT id FROM roles WHERE name = 'USER'), 'It takes courage to grow up and become who you really are.'),
    ('jaylen.wilkerson@libero.it', 'jaylen_wilkerson', 'BDEAB84B54D7939CE6EA7E3590DD33A8D11FB1B', (SELECT id FROM roles WHERE name = 'USER'), 'Accept no one�s definition of your life, but define yourself.'),
    ('cali.henson@sky.com', 'cali_henson', 'ADBE705596A4297E6C5C5ED36EC071434985CF6B', (SELECT id FROM roles WHERE name = 'USER'), 'The imitator dooms himself to hopeless mediocrity.'),
    ('hezekiah.benton@hotmail.it', 'hezekiah_benton', '782589E9BBA1822310E716A7E72827B1BDD3037D', (SELECT id FROM roles WHERE name = 'USER'), 'To be yourself in a world that is constantly trying to make you something else is the greatest accomplishment.'),
    ('guillermo.quintero@yandex.ru', 'guillermo_quintero', 'BC700968813679E091EDB39C4143206D61D248ED', (SELECT id FROM roles WHERE name = 'USER'), 'You may not control all the events that happen to you, but you can decide not to be reduced by them.'),
    ('sloane.bass@mac.com', 'sloane_bass', '65FA719E8C8B766DDCB8928DABA14BBB4D54161D', (SELECT id FROM roles WHERE name = 'USER'), 'Do not fear to be eccentric in opinion, for every opinion now accepted was once eccentric.'),
    ('zoya.sampson@aim.com', 'zoya_sampson', 'B66B9326681A0F61744A798687188EDF28AE1ABF', (SELECT id FROM roles WHERE name = 'USER'), 'The most contrarian thing of all is not to oppose the crowd but to think for yourself.'),
    ('marcus.huang@yahoo.com.au', 'marcus_huang', '3011E039BAC8D29584676B0EBC909906A911689', (SELECT id FROM roles WHERE name = 'USER'), 'When someone is properly grounded in life, they shouldn�t have to look outside themselves for approval.');
