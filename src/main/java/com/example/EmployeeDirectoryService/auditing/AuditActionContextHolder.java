package com.example.EmployeeDirectoryService.auditing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditActionContextHolder {
    private static final ThreadLocal<String> currentAction = new ThreadLocal<>();

    public static void setAction(String action) {
        if (action == null || action.trim().isEmpty()) {
            log.warn("Attempted to set an empty or null audit action. This might indicate a missing action in service layer.");
            currentAction.set("UNKNOWN_ACTION");
        } else {
            currentAction.set(action);
            log.debug("Audit action set to: {}", action);
        }
    }

    public static String getAction() {
        return currentAction.get();
    }

    public static void clearAction() {
        log.debug("Clearing audit action from context.");
        currentAction.remove();
    }
}
