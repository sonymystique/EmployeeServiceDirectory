CREATE TABLE audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    entity_id BIGINT NOT NULL,
    entity_table_name VARCHAR(255) NOT NULL,
    action_done VARCHAR(50) NOT NULL,
    created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL, -- Will be populated from Spring Security
    updated_timestamp TIMESTAMP NULL,
    updated_by VARCHAR(255) NULL,     -- Will be populated from Spring Security
    old_values TEXT, -- Or JSON column type depending on your DB
    new_values TEXT, -- Or JSON column type depending on your DB
    PRIMARY KEY (id)
);