package com.example.sdfernandobrizuela.utils.mappers.clienteMapper;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.ClienteWithDetalleDto;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.utils.mappers.AbstractMapper;
import org.modelmapper.ModelMapper;

public class ClienteMapper extends AbstractMapper<ClienteBean, ClienteDto> {
    @Override
    public ClienteBean toBean(ClienteDto dto) {
        return modelMapper.map(dto, ClienteBean.class);
    }

    @Override
    public ClienteDto toDto(ClienteBean bean) {
        return modelMapper.map(bean, ClienteDto.class);
    }

    public ClienteWithDetalleDto toClienteWithDetalleDto(ClienteBean bean){
        return modelMapper.map(bean, ClienteWithDetalleDto.class);
    }

}
