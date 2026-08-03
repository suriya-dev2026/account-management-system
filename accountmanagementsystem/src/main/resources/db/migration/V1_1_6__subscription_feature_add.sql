CREATE TABLE subscription_features (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    status varchar(20)
);
CREATE TABLE subscription_plan_features (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    plan_id UUID NOT NULL,
    feature_id UUID NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES subscription_plans(id) ON DELETE CASCADE,
    FOREIGN KEY (feature_id) REFERENCES subscription_features(id) ON DELETE CASCADE,
    CONSTRAINT uk_plan_feature UNIQUE(plan_id, feature_id)
);
CREATE TABLE subscription_organizations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    plan_id UUID NOT NULL,
    status VARCHAR(10) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    auto_renew BOOLEAN DEFAULT TRUE,
    cancelled_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organization_id) REFERENCES organizations(id),
    FOREIGN KEY (plan_id) REFERENCES subscription_plans(id)
);
CREATE TABLE subscription_payments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    subscription_organization_id UUID NOT NULL,
    amount NUMERIC(12, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    payment_provider VARCHAR(50),
    payment_reference VARCHAR(255),
    transaction_id VARCHAR(255),
    status VARCHAR(30) NOT NULL,
    paid_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subscription_organization_id) REFERENCES subscription_organizations(id)
);
CREATE TABLE subscription_usage (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    subscription_organization_id UUID NOT NULL UNIQUE,
    current_members INTEGER DEFAULT 0,
    current_teachers INTEGER DEFAULT 0,
    current_admins INTEGER DEFAULT 0,
    storage_used_mb BIGINT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subscription_organization_id) REFERENCES subscription_organizations(id)
);
CREATE TABLE subscription_audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    old_plan_id UUID,
    new_plan_id UUID,
    action VARCHAR(50) NOT NULL,
    changed_by UUID,
    remarks TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (organization_id) REFERENCES organizations(id),
    FOREIGN KEY (old_plan_id) REFERENCES subscription_plans(id),
    FOREIGN KEY (new_plan_id) REFERENCES subscription_plans(id)
);
CREATE INDEX idx_subscription_organization ON subscription_organizations(organization_id);
CREATE INDEX idx_subscription_status ON subscription_organizations(status);
CREATE INDEX idx_payment_subscription ON subscription_payments(subscription_organization_id);
CREATE INDEX idx_subscription_usage_subscription ON subscription_usage(subscription_organization_id);
CREATE INDEX idx_audit_organization ON subscription_audit_logs(organization_id);