CREATE TABLE pastoral_care (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    member_id UUID NULL,
    visitor_name VARCHAR(100),
    visitor_contact_number VARCHAR(20),
    visit_date DATE,
    visit_type VARCHAR(30),
    notes TEXT,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE meeting_types (
    id SERIAL PRIMARY KEY,
    category VARCHAR(50) NOT NULL,
    description VARCHAR(255)
);
CREATE TABLE meetings (
    id SERIAL PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id),
    meeting_type_id VARCHAR(50) NOT NULL,
    meeting_date DATE NOT NULL,
    start_time TIME,
    end_time TIME,
    description VARCHAR(255)
);
CREATE TABLE meeting_attendance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    meeting_id INTEGER NOT NULL REFERENCES meetings(id),
    member_id UUID NOT NULL,
    attendance_date DATE NOT NULL,
    attendance_status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE baptisms (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    member_id UUID NOT NULL,
    baptism_date DATE,
    notes VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);