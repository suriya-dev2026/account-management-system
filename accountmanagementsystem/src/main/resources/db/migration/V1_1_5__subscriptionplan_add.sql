CREATE TABLE IF NOT EXISTS subscription_plans (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) UNIQUE NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    billing_cycle VARCHAR(20) NOT NULL,
    price NUMERIC(12, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    trial_days INTEGER DEFAULT 0,
    max_students INTEGER,
    max_teachers INTEGER,
    max_admins INTEGER,
    discount_percentage numeric(15, 2) DEFAULT NULL,
    status varchar(10),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);