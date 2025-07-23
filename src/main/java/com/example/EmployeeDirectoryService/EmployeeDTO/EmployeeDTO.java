package com.example.EmployeeDirectoryService.EmployeeDTO;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "email should be valid")
    @NotBlank(message = "Email is required")
    private String email;


}
