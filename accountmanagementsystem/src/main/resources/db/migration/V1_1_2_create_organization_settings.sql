CREATE TABLE organization_settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    logo_url VARCHAR(500),
    favicon_url VARCHAR(500),
    primary_color VARCHAR(20),
    timezone VARCHAR(100),
    currency VARCHAR(10),
    language VARCHAR(20),
    FOREIGN KEY (organization_id) REFERENCES organizations(id)
);