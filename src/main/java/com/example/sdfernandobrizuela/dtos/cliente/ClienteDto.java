package com.example.sdfernandobrizuela.dtos.cliente;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class ClienteDto extends AbstractDto {
    private String nombre;
    private String ruc;
    private String cedula;
}
