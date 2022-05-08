-- https://howtodoinjava.com/spring-boot2/oauth2-auth-server/
-- https://docs.spring.io/spring-security-oauth2-boot/docs/2.2.x-SNAPSHOT/reference/html/boot-features-security-oauth2-authorization-server.html
-- https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/appendix-schema.html
CREATE TABLE users
(

    id           uuid NOT NULL
        CONSTRAINT user_id_pkey
            primary key,
    email        varchar(255),
    enabled      boolean NOT NULL,
    password     varchar(255),
    username     varchar(255)
        CONSTRAINT user_username_key
            UNIQUE

);

CREATE TABLE authorities
(

    username     varchar(50) NOT NULL,
    authority    varchar(50) NOT NULL,
    CONSTRAINT fk_authorities_users foreign key(username) references users(username)

);

create UNIQUE index ix_auth_username on authorities (username,authority);

CREATE TABLE oauth_client_details
(

    client_id               varchar(255) NOT NULL
        CONSTRAINT oauth_client_details_client_id_pkey
            primary key,
    access_token_validity   integer,
    additional_information  varchar(255),
    authorities             varchar(255),
    authorized_grant_types  varchar(255),
    autoapprove             boolean NOT NULL,
    client_secret           varchar(255),
    refresh_token_validity  integer,
    resource_ids            varchar(255),
    scope                   varchar(255),
    web_server_redirect_uri varchar(255)

);


-- task-client
INSERT INTO oauth_client_details (client_id,
                                  client_secret,
                                  authorized_grant_types,
                                  scope,
                                  web_server_redirect_uri,
                                  authorities,
                                  access_token_validity,
                                  refresh_token_validity,
                                  additional_information,
                                  autoapprove)

VALUES ('task-client', -- client-id
        '$2a$10$GUVsPi4TJHlyuQdX2m9zyeozXjz3/1FsMpYHVtuVPqJkyDoQHMwme', -- hash client secret: fBqa60wNm2
        'password,refresh_token', -- grant types
        'tasks', -- scope
        null,
        null,
        3600,
        36000,
        null,
        true);

-- users, hash of password: password

-- 5k5bsAy2 => $2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe
-- https://bcrypt-generator.com/ => 10 rounds

-- https://www.guidgenerator.com/

INSERT INTO users (id, username, email, enabled, password) VALUES ('ce8a11ea-39d5-49d3-b860-84989d6ec87a', 'admin', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('4c4e1ddc-d098-4053-b7d7-41c716f7302a', 'user1', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('ece70fa3-2c59-4c8a-9f8c-4d7bd201fa3d', 'user2', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('0c92e1dd-ba0d-479b-a8c6-0262a2b240ac', 'user3', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('c93d5933-458b-46d4-8865-f30c27a6d7a8', 'user4', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('5a57c3ac-1399-4c1e-9967-19e6ca2ab6b9', 'user5', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');
INSERT INTO users (id, username, email, enabled, password) VALUES ('f886166b-d2f9-4c3b-b219-13a37412c9cd', 'user6', null, true, '$2a$10$sbKz.YS/XGgdGn2fXL1tWeTAVM.6XsVoIwT/kBXo8zPBmx05.VFJe');

-- authorities
INSERT INTO authorities (authority, username) VALUES ('ADMIN', 'admin');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user1');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user2');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user3');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user4');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user5');
INSERT INTO authorities (authority, username) VALUES ('USER', 'user6');
