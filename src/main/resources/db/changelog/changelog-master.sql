-- Drop existing tables if they exist
DROP TABLE IF EXISTS public.user_roles CASCADE;
DROP TABLE IF EXISTS public.role_authorities CASCADE;
DROP TABLE IF EXISTS public.authority CASCADE;
DROP TABLE IF EXISTS public.role CASCADE;
DROP TABLE IF EXISTS public.users CASCADE;

-- Drop existing sequences if they exist
DROP SEQUENCE IF EXISTS public.authority_seq CASCADE;
DROP SEQUENCE IF EXISTS public.role_seq CASCADE;
DROP SEQUENCE IF EXISTS public.user_seq CASCADE;

-- Create new sequences
CREATE SEQUENCE public.authority_seq;
CREATE SEQUENCE public.role_seq;
CREATE SEQUENCE public.user_seq;

CREATE TABLE IF NOT EXISTS public.authority
(
    id bigint DEFAULT nextval('public.authority_seq') NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT authority_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint DEFAULT nextval('public.role_seq') NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.role_authorities
(
    role_id bigint NOT NULL,
    authority_id bigint NOT NULL,
    CONSTRAINT role_authorities_pkey PRIMARY KEY (role_id, authority_id)
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint DEFAULT nextval('public.user_seq') NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    username character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.role_authorities
    ADD CONSTRAINT fk_role_authority_role_id FOREIGN KEY (role_id)
    REFERENCES public.role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.role_authorities
    ADD CONSTRAINT fk_role_authority_authority_id FOREIGN KEY (authority_id)
    REFERENCES public.authority (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.user_roles
    ADD CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.user_roles
    ADD CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id)
    REFERENCES public.role (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

INSERT INTO public.role (id, name) VALUES (nextval('role_seq'), 'ROLE_USER');
INSERT INTO public.role (id, name) VALUES (nextval('role_seq'), 'ROLE_MANAGER');
INSERT INTO public.role (id, name) VALUES (nextval('role_seq'), 'ROLE_ADMIN');

INSERT INTO public.users (email, first_name, last_name, password, username) VALUES
('ahmedsamir66@gmail.com', 'Ahmed', 'Samir', '$2a$12$GKDfUUd.ESU36JJvFRuSZeFAxP8JMd.OsWphLoVEGUPUOnxOHmi2C', 'senwar');

INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'READ_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'WRITE_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'CREATE_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'DELETE_PRIVILEGES');

-- ROLE_ADMIN has all privileges
INSERT INTO public.role_authorities (role_id, authority_id) VALUES
((SELECT id FROM public.role WHERE name='ROLE_ADMIN'), (SELECT id FROM public.authority WHERE name='READ_PRIVILEGES')),
((SELECT id FROM public.role WHERE name='ROLE_ADMIN'), (SELECT id FROM public.authority WHERE name='WRITE_PRIVILEGES')),
((SELECT id FROM public.role WHERE name='ROLE_ADMIN'), (SELECT id FROM public.authority WHERE name='DELETE_PRIVILEGES')),
((SELECT id FROM public.role WHERE name='ROLE_ADMIN'), (SELECT id FROM public.authority WHERE name='CREATE_PRIVILEGES'));

-- ROLE_MANAGER has limited privileges
INSERT INTO public.role_authorities (role_id, authority_id) VALUES
((SELECT id FROM public.role WHERE name='ROLE_MANAGER'), (SELECT id FROM public.authority WHERE name='READ_PRIVILEGES')),
((SELECT id FROM public.role WHERE name='ROLE_MANAGER'), (SELECT id FROM public.authority WHERE name='WRITE_PRIVILEGES')),
((SELECT id FROM public.role WHERE name='ROLE_MANAGER'), (SELECT id FROM public.authority WHERE name='CREATE_PRIVILEGES'));

-- ROLE_USER has read-only access
INSERT INTO public.role_authorities (role_id, authority_id) VALUES
((SELECT id FROM public.role WHERE name='ROLE_USER'), (SELECT id FROM public.authority WHERE name='READ_PRIVILEGES'));


INSERT INTO public.user_roles (user_id, role_id) VALUES ((select id from users where email ='ahmedsamir66@gmail.com'), (select id from public.role where name='ROLE_ADMIN'));
