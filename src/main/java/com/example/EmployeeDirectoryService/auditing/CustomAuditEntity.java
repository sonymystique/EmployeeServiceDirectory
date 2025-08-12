package com.example.EmployeeDirectoryService.auditing;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Table(name = "audit_log") // Name of your custom audit table
@RevisionEntity(CustomRevisionListener.class)
@Data
@Getter
@Setter
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "revision_id"))
})
public class CustomAuditEntity extends DefaultRevisionEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "action_done")
    private String actionDone;

    @LastModifiedDate
    private Date lastModifiedTimeStamp;


    // Getters and setters (omitted for brevity)
}

