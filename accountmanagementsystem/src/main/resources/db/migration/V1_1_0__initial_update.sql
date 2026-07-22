CREATE TABLE IF NOT EXISTS organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(25) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    registration_number VARCHAR(100),
    email VARCHAR(150) NOT NULL,
    contact_number VARCHAR(30) NOT NULL,
    website VARCHAR(255),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    primary_contact_name VARCHAR(150),
    primary_contact_email VARCHAR(150),
    primary_contact_phone VARCHAR(30),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    user_name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    contact_number varchar(30) UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(25),
    failed_login_attempts INTEGER DEFAULT 0,
    is_account_locked BOOLEAN DEFAULT FALSE,
    locked_time TIMESTAMP,
    status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS user_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    gender varchar(20) NOT NULL,
    address VARCHAR(255),
    date_of_birth DATE,
    status VARCHAR(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS user_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    otp VARCHAR(255),
    otp_expiration TIMESTAMP,
    otp_verification_count INTEGER DEFAULT 0,
    is_otp_verified BOOLEAN DEFAULT FALSE,
    refresh_key VARCHAR(255),
    refresh_key_created_at TIMESTAMP,
    refresh_key_expiration TIMESTAMP,
    refresh_key_status BOOLEAN DEFAULT FALSE,
    is_valid_token BOOLEAN DEFAULT FALSE,
    session_status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS user_login_audit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    user_id UUID NOT NULL,
    logged_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    action VARCHAR(50),
    logged_ip VARCHAR(50),
    logged_browser VARCHAR(255),
    os_version VARCHAR(50),
    is_mobile BOOLEAN,
    attempted_username VARCHAR(100),
    attempted_password VARCHAR(250),
    returned_result VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS password_resets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    otp VARCHAR(255),
    otp_expiration TIMESTAMP,
    otp_verification_count INTEGER DEFAULT 0,
    is_otp_verified BOOLEAN DEFAULT FALSE,
    reset_token varchar(255),
    reset_token_expiry TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS email_queues (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID,
    to_email VARCHAR(150) NOT NULL,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sent_at TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS member_category (
    id SERIAL PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS locations (
    id serial PRIMARY KEY,
    location VARCHAR(50) NOT NULL,
    description varchar(255) null,
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS members (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    organization_code VARCHAR(25) NOT NULL,
    family_head_id UUID,
    category VARCHAR(30) not null,
    location varchar(30) not null,
    relation_ship varchar(50),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) UNIQUE not null,
    gender VARCHAR(20) not null,
    wedding_Date date,
    date_of_birth DATE not null,
    date_of_join DATE not null,
    email VARCHAR(150) not null,
    contact_number VARCHAR(20) not null,
    address VARCHAR(255) not null,
    is_water_baptised boolean default false,
    is_spirit_baptised boolean default false,
    status VARCHAR(20),
    created_at TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(family_head_id) REFERENCES members(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
create sequence organization_code_seq start with 1 increment by 1;
CREATE INDEX idx_organization_status ON organizations(status);
CREATE INDEX idx_members_name ON members(first_name, last_name);