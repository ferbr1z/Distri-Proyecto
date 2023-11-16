package com.example.sdfernandobrizuela.dtos.cliente;

import lombok.Data;

@Data
public class ClienteWithDetalleDto extends ClienteDto {
    private ClienteDetalleDto clienteDetalle;
}
