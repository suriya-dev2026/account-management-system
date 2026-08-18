CREATE TABLE organization_audit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_code VARCHAR(25) NOT NULL,
    user_id UUID,
    logged_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    entity_name VARCHAR(100),
    entity_pk VARCHAR(255),
    action_name VARCHAR(50),
    existing_value TEXT,
    updated_value TEXT,
    remarks TEXT,
    ip_address VARCHAR(45)
);