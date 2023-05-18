-- auto-generated definition
create table oauth_access_token
(
    token_id          varchar(256),
    token             bytea,
    authentication_id varchar(256),
    user_name         varchar(256),
    client_id         varchar(256),
    authentication    bytea,
    refresh_token     varchar(256)
);

-- alter table oauth_access_token
--     owner to postgres;

-- auto-generated definition
create table oauth_client_details
(
    client_id               varchar(256) not null
        constraint oauth_client_details_pkey
            primary key,
    resource_ids            varchar(256),
    client_secret           varchar(256),
    scope                   varchar(256),
    authorized_grant_types  varchar(256),
    web_server_redirect_uri varchar(256),
    authorities             varchar(256),
    access_token_validity   integer,
    refresh_token_validity  integer,
    additional_information  varchar(4096),
    autoapprove             varchar(256)
);

-- alter table oauth_client_details
--     owner to postgres;

-- auto-generated definition
create table oauth_client_token
(
    token_id          varchar(256),
    token             bytea,
    authentication_id varchar(256),
    user_name         varchar(256),
    client_id         varchar(256)
);

-- alter table oauth_client_token
--     owner to postgres;

INSERT INTO public.oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove) VALUES ('clientId', null, '{bcrypt}$2a$10$vCXMWCn7fDZWOcLnIEhmK.74dvK1Eh8ae2WrWlhr2ETPLoxQctN4.', 'read,write', 'password,refresh_token,client_credentials', null, 'ROLE_CLIENT', 3600000, 3600000, null, null);




-- auto-generated definition
create table oauth_refresh_token
(
    token_id       varchar(256),
    token          bytea,
    authentication bytea
);

-- alter table oauth_refresh_token
--     owner to postgres;

