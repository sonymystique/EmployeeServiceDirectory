package com.example.EmployeeDirectoryService.employeeDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @NotEmpty(message = "Full name is required")
    private String fullName;
    @Email(message = "email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;
    private LocalDateTime createdTimeStamp;
    private LocalDateTime lastModifiedTimeStamp;
}
