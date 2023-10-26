package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class ClienteDetalleWithClienteDto extends ClienteDetalleDto {
    private ClienteDto cliente;
}