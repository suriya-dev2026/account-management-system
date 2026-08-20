CREATE TABLE sunday_school_attendance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    student_id UUID NOT NULL,
    attendance_date DATE NOT NULL,
    attendance_status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(student_id) REFERENCES sunday_school_students(id),
    FOREIGN KEY(organization_id) REFERENCES organizations(id)
);
CREATE TABLE sunday_school_transitions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id UUID NOT NULL REFERENCES sunday_school_students(id),
    from_class_id UUID NOT NULL REFERENCES sunday_school_classes(id),
    to_class_id UUID NOT NULL REFERENCES sunday_school_classes(id),
    transition_by UUID NOT NULL REFERENCES sunday_school_teachers(id),
    transition_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE sunday_school_attendance
ADD CONSTRAINT uk_sunday_school_attendance UNIQUE (organization_id, student_id, attendance_date);