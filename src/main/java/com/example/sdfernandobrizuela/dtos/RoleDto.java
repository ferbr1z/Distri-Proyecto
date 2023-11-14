package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto extends AbstractDto {
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
}
