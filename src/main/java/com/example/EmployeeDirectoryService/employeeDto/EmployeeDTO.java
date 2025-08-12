package com.example.EmployeeDirectoryService.employeeDto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDTO  {

    @NotEmpty(message = "Full name is required")
    @Schema(description = "Employee's fullname")
    private String fullName;
    @Email(message = "email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    @CreatedDate
    private LocalDateTime createdTimeStamp;
    @LastModifiedDate
    private LocalDateTime lastModifiedTimeStamp;
}
