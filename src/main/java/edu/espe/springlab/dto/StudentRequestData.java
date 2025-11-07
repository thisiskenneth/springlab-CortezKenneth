package edu.espe.springlab.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class StudentRequestData {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 120, message = "El nombre debe tener entre 3 y 120 caracteres")
    private String fullName;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ingresar un correo válido")
    @Size(max = 120, message = "El correo no puede superar 120 caracteres")
    private String email;

    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate birthDate;

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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
