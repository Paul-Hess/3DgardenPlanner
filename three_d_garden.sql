--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: gardens; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE gardens (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    user_id integer NOT NULL,
    length integer NOT NULL,
    width integer NOT NULL,
    garden_name character varying NOT NULL
);


ALTER TABLE gardens OWNER TO home;

--
-- Name: gardens_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE gardens_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gardens_id_seq OWNER TO home;

--
-- Name: gardens_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE gardens_id_seq OWNED BY gardens.id;


--
-- Name: gardens_plants; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE gardens_plants (
    id integer NOT NULL,
    garden_id integer NOT NULL,
    plant_id integer NOT NULL
);


ALTER TABLE gardens_plants OWNER TO home;

--
-- Name: gardens_plants_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE gardens_plants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gardens_plants_id_seq OWNER TO home;

--
-- Name: gardens_plants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE gardens_plants_id_seq OWNED BY gardens_plants.id;


--
-- Name: plants; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE plants (
    id integer NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    user_id integer,
    plant_name character varying NOT NULL,
    latin_name character varying NOT NULL,
    usda_zone character varying NOT NULL,
    average_height integer NOT NULL,
    average_width integer NOT NULL,
    plant_icon_url character varying NOT NULL,
    active_season character varying
);


ALTER TABLE plants OWNER TO home;

--
-- Name: plants_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE plants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE plants_id_seq OWNER TO home;

--
-- Name: plants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE plants_id_seq OWNED BY plants.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    user_name character varying NOT NULL,
    email character varying NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    password character varying NOT NULL
);


ALTER TABLE users OWNER TO home;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO home;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY gardens ALTER COLUMN id SET DEFAULT nextval('gardens_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY gardens_plants ALTER COLUMN id SET DEFAULT nextval('gardens_plants_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY plants ALTER COLUMN id SET DEFAULT nextval('plants_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: gardens; Type: TABLE DATA; Schema: public; Owner: home
--

COPY gardens (id, created_at, updated_at, user_id, length, width, garden_name) FROM stdin;
\.


--
-- Name: gardens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('gardens_id_seq', 1, false);


--
-- Data for Name: gardens_plants; Type: TABLE DATA; Schema: public; Owner: home
--

COPY gardens_plants (id, garden_id, plant_id) FROM stdin;
\.


--
-- Name: gardens_plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('gardens_plants_id_seq', 1, false);


--
-- Data for Name: plants; Type: TABLE DATA; Schema: public; Owner: home
--

COPY plants (id, created_at, updated_at, user_id, plant_name, latin_name, usda_zone, average_height, average_width, plant_icon_url, active_season) FROM stdin;
\.


--
-- Name: plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('plants_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: home
--

COPY users (id, user_name, email, created_at, updated_at, password) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Name: gardens_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY gardens
    ADD CONSTRAINT gardens_pkey PRIMARY KEY (id);


--
-- Name: gardens_plants_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY gardens_plants
    ADD CONSTRAINT gardens_plants_pkey PRIMARY KEY (id);


--
-- Name: plants_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY plants
    ADD CONSTRAINT plants_pkey PRIMARY KEY (id);


--
-- Name: unique_email; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: unique_user_name; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT unique_user_name UNIQUE (user_name);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: home
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM home;
GRANT ALL ON SCHEMA public TO home;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

