CREATE TABLE audit_log (
    id BIGINT NOT NULL AUTO_INCREMENT,
    entity_id BIGINT NOT NULL,
    entity_table_name VARCHAR(255) NOT NULL,
    action_done VARCHAR(50) NOT NULL,
    created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    old_values TEXT,
    new_values TEXT,
    PRIMARY KEY (id)
);
CREATE INDEX idx_audit_log_entity_id ON audit_log (entity_id);
         CREATE INDEX idx_audit_log_entity_table_name ON audit_log (entity_table_name);
         CREATE INDEX idx_audit_log_created_by ON audit_log (updated_by);
         CREATE INDEX idx_audit_log_created_timestamp ON audit_log (created_timestamp);