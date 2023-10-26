package com.example.sdfernandobrizuela.dtos;

import lombok.Data;

@Data
public class ProveedorDetalleWithProveedorDto extends ProveedorDetalleDto{
    private ProveedorDto proveedor;
}