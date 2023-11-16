package com.example.sdfernandobrizuela.utils.mappers.clienteMapper;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.cliente.ClienteDto;
import com.example.sdfernandobrizuela.utils.mappers.AbstractMapper;

public class ClienteMapper extends AbstractMapper<ClienteBean, ClienteDto> {
    @Override
    public ClienteBean toBean(ClienteDto dto) {
        return modelMapper.map(dto, ClienteBean.class);
    }

    @Override
    public ClienteDto toDto(ClienteBean bean) {
        return modelMapper.map(bean, ClienteDto.class);
    }

}
