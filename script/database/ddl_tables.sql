CREATE TABLE public.swuser
(
    id SERIAL,
    name character varying(60) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(33) COLLATE pg_catalog."default" NOT NULL,
    created  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    photo bytea,
    enable boolean NOT NULL DEFAULT false,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_email_key UNIQUE (email)
);

CREATE TABLE public.sworganization
(
    id SERIAL,
    admin integer NOT NULL,
    name character varying(80) COLLATE pg_catalog."default" NOT NULL,
    description character varying(500) COLLATE pg_catalog."default",
    created timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT organization_pk PRIMARY KEY (id),
    CONSTRAINT organization_fk0 FOREIGN KEY (admin)
        REFERENCES public.swuser (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.swprocess (
	id SERIAL NOT NULL,
	name character varying(50),
	description character varying(500),
	images bytea,
	modified DATE,
	organization integer NOT NULL,
	created timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated TIMESTAMP NOT NULL,
	CONSTRAINT swprocess_pk PRIMARY KEY (id)
);

CREATE TABLE public.swactivity (
	id serial NOT NULL,
	name character varying(50),
	description character varying(400),
	swphase integer,
	mandatory BOOLEAN NOT NULL DEFAULT false,
	swguidance integer,
	swrole integer,
	execorder integer NOT NULL UNIQUE,
	start bool NOT NULL,
	CONSTRAINT swactivity_pk PRIMARY KEY (id)
);

create table public.swguidance(id serial primary key,
			        name varchar(50),
			        description varchar (400),
			        guidefile bytea
);


CREATE TABLE public.swphase (
	id serial PRIMARY KEY NOT NULL,
	name character varying(50),
	description character varying(400),
	swprocess integer,
	execorder integer NOT NULL UNIQUE
	);

CREATE TABLE public.swactivflow (
        swactivity integer NOT NULL,
        nextactivity integer NOT NULL,
        CONSTRAINT swactivflow_pk PRIMARY KEY (swactivity , nextactivity )
);

CREATE TABLE public.swgateway( 
        id serial NOT NULL,
        annotation varchar(40),
        type character,
        CONSTRAINT swgateway_pk PRIMARY KEY (id) 
);

CREATE TABLE public.swimages (
	id serial NOT NULL,
	swprocess integer NOT NULL,
	image bytea NOT NULL,
	name character varying(40) NOT NULL,
	description character varying(200) NOT NULL,
	CONSTRAINT swimages_pk PRIMARY KEY (id)
);

CREATE TABLE public.swuserprocess( 
        user_id integer NOT NULL,
        swprocess serial NOT NULL,
        write boolean NOT NULL DEFAULT 'false',
        CONSTRAINT userswprocess_pk PRIMARY KEY (user_id, swprocess, write) 
);