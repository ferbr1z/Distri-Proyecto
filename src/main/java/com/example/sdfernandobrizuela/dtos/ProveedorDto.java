package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class ProveedorDto extends AbstractDto {
    private String nombre;
    private String ruc;
    private ProveedorDetalleDto proveedorDetalle;

}
