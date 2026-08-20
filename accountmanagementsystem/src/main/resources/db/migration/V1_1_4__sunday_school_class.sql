CREATE TABLE sunday_school_classes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    class_name VARCHAR(50) NOT NULL,
    class_number INTEGER,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE sunday_school_teachers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    member_id UUID NOT NULL,
    class_id UUID NOT NULL,
    date_of_join DATE,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(id),
    FOREIGN KEY(class_id) REFERENCES sunday_school_classes(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE sunday_school_students (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    member_id UUID NULL,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(20),
    age INTEGER,
    date_of_birth DATE,
    class_id UUID NOT NULL,
    teacher_id UUID,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(member_id) REFERENCES members(id),
    FOREIGN KEY(class_id) REFERENCES sunday_school_classes(id),
    FOREIGN KEY(teacher_id) REFERENCES sunday_school_teachers(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);