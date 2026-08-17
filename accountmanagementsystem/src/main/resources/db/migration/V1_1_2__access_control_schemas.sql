CREATE TABLE access_control_roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE access_control_routes (
    id SERIAL PRIMARY KEY,
    controller_name VARCHAR(255) NOT NULL,
    backend_route VARCHAR(255),
    frontend_route VARCHAR(255),
    description TEXT,
    is_default INTEGER DEFAULT 0,
    status VARCHAR(50)
);
CREATE TABLE access_control_module_presets (
    id SERIAL PRIMARY KEY,
    module_name VARCHAR(25),
    preset_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    status VARCHAR(10)
);
CREATE TABLE access_control_route_preset_access (
    id SERIAL PRIMARY KEY,
    module_preset_id INTEGER NOT NULL,
    route_id INTEGER,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (module_preset_id) REFERENCES access_control_module_presets(id),
    FOREIGN KEY (route_id) REFERENCES access_control_routes(id)
);
CREATE TABLE access_control_role_preset_access (
    id SERIAL PRIMARY KEY,
    role_id UUID NOT NULL,
    module_preset_id INTEGER NOT NULL,
    status VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES access_control_roles(id),
    FOREIGN KEY (module_preset_id) REFERENCES access_control_module_presets(id)
);
CREATE TABLE access_control_user_roles (
    id SERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES access_control_roles(id)
);