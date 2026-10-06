CREATE TABLE accounting_bank_account_types (
    id SERIAL PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id),
    type_name VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE'))
);
CREATE TABLE accounting_bank_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    bank_account_type_id INTEGER REFERENCES accounting_bank_account_types(id),
    account_id UUID Not NULL REFERENCES accounting_accounts(id),
    bank_name VARCHAR(100) NOT NULL,
    branch VARCHAR(100),
    account_number VARCHAR(30) UNIQUE NOT NULL,
    ifsc VARCHAR(20),
    account_holder_name VARCHAR(100) NOT NULL,
    opening_balance NUMERIC(14, 2) DEFAULT 0 CHECK (opening_balance >= 0),
    current_balance NUMERIC(14, 2) DEFAULT 0 CHECK (current_balance >= 0),
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE accounting_bank_transaction_types (
    id SERIAL PRIMARY KEY,
    organization_id UUID NOT NULL REFERENCES organizations(id),
    type_name VARCHAR(50) UNIQUE NOT NULL,
    account_id UUID Not NULL REFERENCES accounting_accounts(id),
    direction VARCHAR(50),
    status VARCHAR(20) DEFAULT 'ACTIVE' CHECK (status IN ('ACTIVE', 'INACTIVE'))
);
CREATE TABLE accounting_bank_transactions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    bank_account_id UUID NOT NULL REFERENCES accounting_bank_accounts(id),
    journal_entry_id UUID REFERENCES accounting_journal_entries(id),
    journal_entry_tracking_id VARCHAR(50) NOT NULL,
    transaction_type_id integer,
    reference VARCHAR(100),
    amount NUMERIC(14, 2) NOT NULL CHECK (amount >= 0),
    transaction_date DATE NOT NULL,
    remarks TEXT,
    status VARCHAR(20) DEFAULT 'POSTED' CHECK (status IN ('POSTED', 'DRAFT', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE accounting_audit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL REFERENCES organizations(id),
    user_id UUID NOT NULL REFERENCES users(id),
    logged_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    entity_name VARCHAR(100),
    entity_pk VARCHAR(255),
    action_name VARCHAR(50),
    existing_value TEXT,
    updated_value TEXT,
    remarks TEXT,
    ip_address VARCHAR(45)
);
CREATE SEQUENCE accounting_journal_tracking_seq START WITH 1 INCREMENT BY 1;
ALTER TABLE organization_expense_types
ADD COLUMN account_id UUID REFERENCES accounting_accounts(id);