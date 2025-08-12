package com.example.EmployeeDirectoryService.auditing;


import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;

@Component
public class CustomJdbcTemplate {

    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setLoggedUser(String username) {
        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET @logged_user = '" + username + "'");
            } catch (SQLException e) {
                // Log the error
                throw new RuntimeException("Error setting @logged_user session variable: " + e.getMessage(), e);
            }
            return null;
        });
    }

    public void clearLoggedUser() {
        jdbcTemplate.execute((ConnectionCallback<Object>) connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET @logged_user = NULL"); // Clear the session variable
            } catch (SQLException e) {
                // Log the error
                throw new RuntimeException("Error clearing @logged_user session variable: " + e.getMessage(), e);
            }
            return null;
        });
    }
}
