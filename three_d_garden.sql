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
    plant_id integer NOT NULL,
    list_position integer
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
-- Name: plants_companions; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE plants_companions (
    id integer NOT NULL,
    plant_id integer,
    companion_plant_id integer
);


ALTER TABLE plants_companions OWNER TO home;

--
-- Name: plants_companions_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE plants_companions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE plants_companions_id_seq OWNER TO home;

--
-- Name: plants_companions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE plants_companions_id_seq OWNED BY plants_companions.id;


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
    password character varying NOT NULL,
    is_admin boolean DEFAULT false NOT NULL
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
-- Name: users_plants; Type: TABLE; Schema: public; Owner: home; Tablespace: 
--

CREATE TABLE users_plants (
    id integer NOT NULL,
    user_id integer NOT NULL,
    plant_id integer NOT NULL
);


ALTER TABLE users_plants OWNER TO home;

--
-- Name: users_plants_id_seq; Type: SEQUENCE; Schema: public; Owner: home
--

CREATE SEQUENCE users_plants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_plants_id_seq OWNER TO home;

--
-- Name: users_plants_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: home
--

ALTER SEQUENCE users_plants_id_seq OWNED BY users_plants.id;


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

ALTER TABLE ONLY plants_companions ALTER COLUMN id SET DEFAULT nextval('plants_companions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: home
--

ALTER TABLE ONLY users_plants ALTER COLUMN id SET DEFAULT nextval('users_plants_id_seq'::regclass);


--
-- Data for Name: gardens; Type: TABLE DATA; Schema: public; Owner: home
--

COPY gardens (id, created_at, updated_at, user_id, length, width, garden_name) FROM stdin;
4	2016-05-18 10:56:03.234	2016-05-18 10:56:03.234	1	1	2	garden tester
7	2016-05-19 19:00:16.325	2016-05-19 19:00:16.325	1	12	8	garden first
8	2016-05-19 19:02:03.028	2016-05-19 19:02:03.028	1	10	6	garden second
9	2016-05-19 19:06:26.706	2016-05-19 19:06:26.706	1	10	10	garden three
10	2016-05-19 19:08:45.541	2016-05-19 19:08:45.541	1	10	10	garden five
11	2016-05-19 19:12:54.84	2016-05-19 19:12:54.84	1	10	10	kflaksdjflk
12	2016-05-19 19:14:39.547	2016-05-19 19:14:39.547	1	10	10	sdflkasjdf
13	2016-05-19 19:16:33.986	2016-05-19 19:16:33.986	1	10	10	asldflk
14	2016-05-19 19:19:50.168	2016-05-19 19:19:50.168	1	10	10	sldkfjlskdf
15	2016-05-19 19:23:07.402	2016-05-19 19:23:07.402	1	10	10	lskdjf
16	2016-05-19 19:25:53.289	2016-05-19 19:25:53.289	1	10	10	sdflkjsdlf
17	2016-05-19 19:29:50.806	2016-05-19 19:29:50.806	1	10	10	sdjkfjkshdf
18	2016-05-19 21:10:13.915	2016-05-19 21:10:13.915	1	10	10	dfkjslkdfj
19	2016-05-19 21:24:36.34	2016-05-19 21:24:36.34	1	10	10	s;dkfjlsk
20	2016-05-19 21:28:56.092	2016-05-19 21:28:56.092	1	10	10	slkdfjlskdjf
21	2016-05-19 21:34:17.318	2016-05-19 21:34:17.318	1	10	10	kfjsldfkj
22	2016-05-19 21:38:16.245	2016-05-19 21:38:16.245	1	10	10	kfjslkfj
23	2016-05-19 21:39:35.506	2016-05-19 21:39:35.506	1	10	10	slkdjfks
24	2016-05-19 21:42:55.942	2016-05-19 21:42:55.942	1	10	10	sdfsdf
25	2016-05-19 21:45:39.947	2016-05-19 21:45:39.947	1	10	10	lkdfjlksjdf
26	2016-05-19 21:48:51.026	2016-05-19 21:48:51.026	1	10	10	lkdjflksjdf
27	2016-05-19 21:50:38.54	2016-05-19 21:50:38.54	1	10	10	djkfhskjdfh
28	2016-05-19 21:57:12.569	2016-05-19 21:57:12.569	1	10	10	lkdjflksjd
29	2016-05-19 22:01:28.78	2016-05-19 22:01:28.78	1	10	10	lkdjflksjf
30	2016-05-19 22:02:30.873	2016-05-19 22:02:30.873	1	10	10	lkdjflksjf
31	2016-05-19 22:04:18.207	2016-05-19 22:04:18.207	1	10	10	lkdjflksdjf
32	2016-05-19 22:13:27.87	2016-05-19 22:13:27.87	1	11	11	sdkfjslkdfj
33	2016-05-19 22:19:03.504	2016-05-19 22:19:03.504	1	10	10	sldfkjsldkf
34	2016-05-19 22:33:19.064	2016-05-19 22:33:19.064	1	11	11	kjdflkjsdf
35	2016-05-19 22:40:41.367	2016-05-19 22:40:41.367	1	10	10	new plot
36	2016-05-19 22:44:04.875	2016-05-19 22:44:04.875	1	10	10	other plot
37	2016-05-19 22:50:23.871	2016-05-19 22:50:23.871	1	10	10	dlfkjs
38	2016-05-19 22:52:03.55	2016-05-19 22:52:03.55	1	10	10	sldkfjs
39	2016-05-19 22:53:54.03	2016-05-19 22:53:54.03	1	10	20	sdlfjlksjdf
40	2016-05-19 23:01:15.626	2016-05-19 23:01:15.626	1	10	10	lkdfjlskdfj
41	2016-05-19 23:02:58.034	2016-05-19 23:02:58.034	1	10	10	sldkfjs
42	2016-05-19 23:20:02.167	2016-05-19 23:20:02.167	1	10	10	ljsldkf
43	2016-05-19 23:34:52.496	2016-05-19 23:34:52.496	1	10	10	lkdjflks
44	2016-05-19 23:37:19.11	2016-05-19 23:37:19.11	1	10	10	lskdjflksjdf
45	2016-05-19 23:39:55.937	2016-05-19 23:39:55.937	1	10	10	jsdfkjhsd
46	2016-05-19 23:46:55.915	2016-05-19 23:46:55.915	1	10	10	ldkjflskjflk
47	2016-05-19 23:51:00.407	2016-05-19 23:51:00.407	1	10	10	dkgjlsdkjg
48	2016-05-20 07:55:16.177	2016-05-20 07:55:16.177	1	10	10	sldkfjlks
49	2016-05-20 07:58:40.27	2016-05-20 07:58:40.27	1	10	10	sldkfj
50	2016-05-20 08:00:19.124	2016-05-20 08:00:19.124	1	10	10	slkdjflksdjfd
51	2016-05-20 08:01:37.226	2016-05-20 08:01:37.226	1	10	10	lskdjfksdjf
\.


--
-- Name: gardens_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('gardens_id_seq', 51, true);


--
-- Data for Name: gardens_plants; Type: TABLE DATA; Schema: public; Owner: home
--

COPY gardens_plants (id, garden_id, plant_id, list_position) FROM stdin;
1	4	4	\N
2	4	4	\N
18	13	12	0
19	15	12	0
20	39	12	0
21	39	19	1
22	42	19	0
23	43	19	0
24	43	18	1
25	44	19	0
26	45	19	0
27	46	19	0
30	47	18	2
31	47	15	3
28	47	13	0
29	47	12	1
32	48	17	0
33	48	17	1
34	48	12	2
35	51	16	0
\.


--
-- Name: gardens_plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('gardens_plants_id_seq', 35, true);


--
-- Data for Name: plants; Type: TABLE DATA; Schema: public; Owner: home
--

COPY plants (id, created_at, updated_at, plant_name, latin_name, usda_zone, average_height, average_width, plant_icon_url, active_season) FROM stdin;
12	2016-05-19 14:20:46.399	2016-05-19 14:20:46.399	fennel	foeniculum vulgare	3-10	4	1	fennel.png	summer
13	2016-05-19 14:21:11.465	2016-05-19 14:21:11.465	yarrow	achillea millefolium	3-10	4	2	yarrow.png	summer
14	2016-05-19 14:21:49.042	2016-05-19 14:21:49.042	beet	beta vulgaris	2 - 10	2	1	beet.png	spring summer fall
15	2016-05-19 14:22:36.039	2016-05-19 14:22:36.039	mint	mentha ssp.	4 - 7	3	1	mint.png	summer
16	2016-05-19 14:23:39.945	2016-05-19 14:23:39.945	strawberry	fragaria vesca	3-10	1	2	strawberry.png	spring summer
17	2016-05-19 15:21:57.16	2016-05-19 15:21:57.16	rose	rosa ssp.	3-10	6	4	rose.png	spring summer
18	2016-05-19 15:24:05.397	2016-05-19 15:24:05.397	blackberry	rubus ursinus	2-11	8	3	blackberry.png	summer
19	2016-05-19 15:26:51.274	2016-05-19 15:26:51.274	cauliflower	brassica oleracea	4-10	4	4	cauliflower.png	summer
\.


--
-- Data for Name: plants_companions; Type: TABLE DATA; Schema: public; Owner: home
--

COPY plants_companions (id, plant_id, companion_plant_id) FROM stdin;
1	4	5
2	5	4
\.


--
-- Name: plants_companions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('plants_companions_id_seq', 2, true);


--
-- Name: plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('plants_id_seq', 19, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: home
--

COPY users (id, user_name, email, created_at, updated_at, password, is_admin) FROM stdin;
1	aaa	a@a.com	2016-05-16 15:38:03.955	2016-05-16 15:38:03.955	$2a$12$btuV78XOzt01YE2OGgLvAOzrjb9.kEqlbe1mR65cbCyhjudxaf6Au	t
2	bName	b@b.com	2016-05-16 21:10:39.94	2016-05-17 09:05:27.639	$2a$12$vylsEGDjczIFCVIG5n3d9OB6tYPepQTEhIohgYmubRqQxVJTbzbhi	f
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('users_id_seq', 2, true);


--
-- Data for Name: users_plants; Type: TABLE DATA; Schema: public; Owner: home
--

COPY users_plants (id, user_id, plant_id) FROM stdin;
1	1	4
\.


--
-- Name: users_plants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: home
--

SELECT pg_catalog.setval('users_plants_id_seq', 1, true);


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
-- Name: plants_companions_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY plants_companions
    ADD CONSTRAINT plants_companions_pkey PRIMARY KEY (id);


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
-- Name: users_plants_pkey; Type: CONSTRAINT; Schema: public; Owner: home; Tablespace: 
--

ALTER TABLE ONLY users_plants
    ADD CONSTRAINT users_plants_pkey PRIMARY KEY (id);


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

