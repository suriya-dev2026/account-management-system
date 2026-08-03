CREATE TABLE IF NOT EXISTS user_verifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE,
    is_user_onboarded BOOLEAN DEFAULT FALSE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    is_subscription_completed BOOLEAN DEFAULT FALSE,
    profile_completed_percentage INTEGER DEFAULT 0,
    failed_login_attempts INTEGER DEFAULT 0,
    is_account_locked BOOLEAN DEFAULT FALSE,
    locked_time TIMESTAMP,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_verifications_user FOREIGN KEY (user_id) REFERENCES users(id)
);