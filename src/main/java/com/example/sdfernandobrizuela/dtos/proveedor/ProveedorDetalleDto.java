package com.example.sdfernandobrizuela.dtos.proveedor;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class ProveedorDetalleDto extends AbstractDto {
    private String direccion;
    private String telefono;
    private String email;
    private Integer proveedorId;
}