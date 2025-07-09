package com.example.EmployeeDirectoryService.EmployeeDTO;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class EmployeeDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    public EmployeeDTO() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public EmployeeDTO(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }


}
