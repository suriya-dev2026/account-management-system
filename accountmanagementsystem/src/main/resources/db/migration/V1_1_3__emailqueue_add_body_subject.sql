ALTER TABLE email_queues
ADD COLUMN subject VARCHAR(255);
ALTER TABLE email_queues
ADD COLUMN body TEXT;