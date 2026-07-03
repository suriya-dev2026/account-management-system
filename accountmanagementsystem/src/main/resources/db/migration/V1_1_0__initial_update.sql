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
CREATE TABLE if not EXISTS user_profiles (
    id varchar(50) PRIMARY KEY,
    user_id varchar(50) NOT NULL UNIQUE,
    address VARCHAR(255),
    failed_login_attempts INTEGER DEFAULT 0,
    is_account_locked BOOLEAN DEFAULT FALSE,
    locked_time TIMESTAMP,
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
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
    is_valid_token boolean not null default false,
    session_status varchar(20),
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id),
    constraint fk_user_session foreign key(user_id) references users(id) on delete cascade
);
create table if not exists users_logs(
    id varchar(50),
    user_id varchar(50) not null,
    user_action varchar(20) not null,
    return_result varchar(30) not null,
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id),
    constraint fk_user_log foreign key(user_id) references users(id)
);
create table if not exists password_reset(
    id varchar(50),
    user_id varchar(50) not null,
    reset_otp varchar(6) default null,
    otp_expiration timestamp default null,
    is_otp_verified boolean default false,
    otp_verification_count int default 0,
    reset_token varchar(50) default null,
    token_expiry timestamp default null,
    created_at timestamp not null,
    updated_at timestamp default null,
    primary key(id),
    constraint fk_password_reset foreign key(user_id) references users(id) on delete cascade
);
create table if not exists email_queues(
    id varchar(50),
    user_id varchar(50) not null,
    to_email varchar(150) not null,
    message text,
    status varchar(20),
    created_at timestamp not null,
    sent_at timestamp,
    primary key(id),
    constraint fk_email_queue foreign key(user_id) references users(id)
);
create index idx_users_sessions_user_id on users_sessions(user_id);
create index idx_users_sessions_refresh_key on users_sessions(refresh_key);
create index idx_password_reset_user_id on password_reset(user_id);
create index idx_email_queues_user_id on email_queues(user_id);
create table IF NOT EXISTS locations(
    id varchar(50),
    area varchar(50) not null,
    description text,
    created_at timestamp not null,
    updated_at timestamp default null
);
