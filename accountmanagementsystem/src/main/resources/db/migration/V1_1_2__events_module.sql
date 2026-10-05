CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    event_name VARCHAR(150),
    event_type VARCHAR(50),
    start_date DATE,
    end_date DATE,
    location VARCHAR(100),
    description TEXT,
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE event_participants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    event_id UUID,
    member_id UUID,
    full_name VARCHAR(100),
    date_of_birth DATE,
    school_grade VARCHAR(20),
    address VARCHAR(255),
    contact_number VARCHAR(20),
    emergency_contact_name VARCHAR(100),
    emergency_contact_number VARCHAR(20),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(event_id) REFERENCES events(id)
);
CREATE TABLE event_attendance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    event_id UUID NOT NULL REFERENCES events(id),
    event_participant_id UUID NOT NULL REFERENCES event_participants(id),
    attendance_date DATE NOT NULL,
    attendance_status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
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
CREATE TABLE broadcasts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    broadcast_type VARCHAR(100) NOT NULL,
    message TEXT NOT NULL,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE broadcast_deliveries (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    broadcast_id UUID NOT NULL REFERENCES broadcasts(id),
    user_id UUID NOT NULL REFERENCES users(id),
    delivery_status VARCHAR(50) NOT NULL,
    sent_to VARCHAR(150) NOT NULL,
    sent_at DATE,
    status VARCHAR(10) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE certificates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    member_id UUID NOT NULL REFERENCES members(id) ON DELETE CASCADE,
    certificate_type VARCHAR(30) NOT NULL,
    member_name VARCHAR(50) NOT NULL,
    father_name VARCHAR(50),
    mother_name VARCHAR(50),
    issue_date DATE NOT NULL,
    event_date DATE,
    issued_by VARCHAR(50),
    certificate_number VARCHAR(20) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE bulletins (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT,
    bulletin_date DATE,
    publish_date DATE,
    expiry_date DATE,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE equipment_categories (
    id SERIAL PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id),
    category_name VARCHAR(100) NOT NULL,
    description TEXT
);
CREATE TABLE equipments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    equipment_name VARCHAR(100) NOT NULL,
    category_id serial,
    serial_no VARCHAR(100) UNIQUE,
    purchase_date DATE,
    purchase_cost NUMERIC(15, 2),
    warranty_expiry_date DATE,
    vendor_details VARCHAR(255),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);