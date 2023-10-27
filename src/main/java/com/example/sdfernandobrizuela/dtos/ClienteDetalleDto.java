package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class ClienteDetalleDto extends AbstractDto {
    private String email;
    private String telefono;
    private String direccion;
    private Integer clienteId;
}
