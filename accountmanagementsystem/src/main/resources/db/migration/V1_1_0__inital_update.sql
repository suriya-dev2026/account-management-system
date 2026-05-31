create table if not exists users(
    id varchar(50),
    first_name varchar(50) not null,
    last_name varchar(50) default null,
    user_name varchar(50) not null,
    email varchar(150) not null unique,
    password varchar(256) not null,
    phone varchar(20) not null unique,
    role varchar(10),
    status varchar(10) not null,
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id)
);
create table if not exists users_sessions(
    id varchar(50),
    user_id varchar(50) not null,
    otp varchar default null,
    otp_expiration timestamp default null,
    otp_verification_count int default 0,
    is_otp_verified boolean not null default false,
    refresh_key varchar(250) default null,
    refresh_key_created_at timestamp default null,
    refresh_key_expiration timestamp default null,
    refresh_key_status boolean not null default false,
    access_token text default null,
    is_valid_token boolean not null default false,
    session_status varchar(20),
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id)
);
create table if not exists users_logs(
    id varchar(50),
    user_id varchar(50) not null,
    user_action varchar(20) not null,
    return_result varchar(30) not null,
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id)
);
create table if not exists password_reset(
    id varchar(50),
    user_id varchar(50) not null,
    reset_otp varchar(6) default null,
    otp_expiration timestamp default null,
    is_otp_verified boolean default false,
    otp_verification_count int default 0,
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id)
);