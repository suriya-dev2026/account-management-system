ALTER TABLE event_participants
ADD COLUMN organization_id UUID;
ALTER TABLE event_participants
ADD CONSTRAINT fk_event_participant_organization FOREIGN KEY (organization_id) REFERENCES organizations(id);
ALTER TABLE event_attendance
ADD COLUMN organization_id UUID NOT NULL;
ALTER TABLE event_attendance
ADD CONSTRAINT fk_event_attendance_organization FOREIGN KEY (organization_id) REFERENCES organizations(id);