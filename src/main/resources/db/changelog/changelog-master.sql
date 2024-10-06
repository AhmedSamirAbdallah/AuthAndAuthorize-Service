
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
INSERT INTO public.role (id, name) VALUES (nextval('role_seq'), 'ROLE_ADMIN');

INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'READ_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'WRITE_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'CREATE_PRIVILEGES');
INSERT INTO public.authority (id, name) VALUES (nextval('authority_seq'), 'DELETE_PRIVILEGES');
