package com.example.sdfernandobrizuela.dtos;

import lombok.Data;

@Data
public class ClienteWithDetailDto extends ClienteDto {
    private ClienteDetalleDto clienteDetalle;
}
