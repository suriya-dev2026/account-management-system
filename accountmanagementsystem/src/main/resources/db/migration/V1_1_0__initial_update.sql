CREATE TABLE public.users (
    id bigint NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50),
    user_name character varying(50) NOT NULL UNIQUE,
    email character varying(100) NOT NULL UNIQUE,
    password character varying(256) NOT NULL,
    phone character varying(50) NOT NULL UNIQUE,
    otp character varying(10),
    otp_expiry timestamp without time zone,
    access_token text,
    refresh_key text,
    status character varying(10),
    created_at time without time zone,
    updated_at time without time zone
);
CREATE SEQUENCE public.users_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;