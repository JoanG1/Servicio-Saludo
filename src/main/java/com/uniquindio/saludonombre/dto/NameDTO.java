package com.uniquindio.saludonombre.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NameDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @NotNull
    private String name;
}
