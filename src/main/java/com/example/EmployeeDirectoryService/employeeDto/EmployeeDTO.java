package com.example.EmployeeDirectoryService.employeeDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotEmpty(message = "Full name is required")
    private String fullName;

    @Email(message = "email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;


}
