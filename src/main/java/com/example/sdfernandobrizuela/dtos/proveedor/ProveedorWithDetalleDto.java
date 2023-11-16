package com.example.sdfernandobrizuela.dtos.proveedor;

import lombok.Data;

@Data
public class ProveedorWithDetalleDto extends ProveedorDto {
    private ProveedorDetalleDto proveedorDetalle;
}
