package com.example.EmployeeDirectoryService.auditing;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;


public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomAuditEntity customAuditEntity = (CustomAuditEntity) revisionEntity;


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            customAuditEntity.setCreatedBy(authentication.getName());
            customAuditEntity.setUpdatedBy(authentication.getName()); // Assume same user for initial audit
        } else {
            customAuditEntity.setCreatedBy("SYSTEM");
            customAuditEntity.setUpdatedBy("SYSTEM");
        }

        String action = AuditActionContextHolder.getAction();
        customAuditEntity.setActionDone(action!=null ? action: "UNKNOWN_ACTION");

        customAuditEntity.setLastModifiedTimeStamp(new Date());
    }

    }
