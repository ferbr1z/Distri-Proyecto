package com.example.sdfernandobrizuela.dtos;

import lombok.Data;

@Data
public class ProveedorWithDetalleDto extends ProveedorDto{
    private ProveedorDetalleDto proveedorDetalle;
}
