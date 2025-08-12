DELIMITER $$
CREATE TRIGGER employees_after_insert
AFTER INSERT ON employees
FOR EACH ROW
BEGIN
    INSERT INTO audit_log (entity_id, entity_table_name, action_done,created_by, updated_by, new_values)
    VALUES (NEW.id, 'employees', 'INSERT',@logged_user, @logged_user, JSON_OBJECT('name', NEW.name, 'employee_email', NEW.employee_email));
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER employees_after_update
AFTER UPDATE ON employees
FOR EACH ROW
BEGIN
    DECLARE temp_created_by VARCHAR(255);

    SELECT created_by
        INTO temp_created_by
        FROM studentdatabase.audit_log
        WHERE entity_id = NEW.id
          AND entity_table_name = 'employees'
          AND action_done = 'INSERT'
        ORDER BY created_timestamp ASC
        LIMIT 1;

    INSERT INTO audit_log (entity_id, entity_table_name, action_done,created_by, created_timestamp, updated_by, old_values, new_values)
    VALUES (NEW.id, 'employees', 'UPDATE',temp_created_by,NEW.created_time_stamp, @logged_user, JSON_OBJECT('name', OLD.name, 'employee_email', OLD.employee_email), JSON_OBJECT('name', NEW.name, 'employee_email', NEW.employee_email));
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER employees_after_delete
AFTER DELETE ON employees
FOR EACH ROW
BEGIN

 DECLARE temp_created_by VARCHAR(255);

    SELECT created_by
        INTO temp_created_by
        FROM studentdatabase.audit_log
        WHERE entity_id = OLD.id
          AND entity_table_name = 'employees'
          AND action_done = 'INSERT'
        ORDER BY created_timestamp ASC
        LIMIT 1;


    INSERT INTO audit_log (entity_id, entity_table_name, action_done,created_by, updated_by, old_values)
    VALUES (NEW.id, 'employees', 'DELETE',@logged_user, @logged_user, JSON_OBJECT('name', OLD.name, 'employee_email', OLD.employee_email));
END$$
DELIMITER ;