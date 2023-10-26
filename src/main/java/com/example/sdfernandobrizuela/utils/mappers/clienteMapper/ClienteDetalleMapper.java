package com.example.sdfernandobrizuela.utils.mappers.clienteMapper;

import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleWithClienteDto;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.utils.mappers.AbstractMapper;

public class ClienteDetalleMapper extends AbstractMapper<ClienteDetalleBean, ClienteDetalleDto> {

    @Override
    public ClienteDetalleBean toBean(ClienteDetalleDto dto) {
        return modelMapper.map(dto, ClienteDetalleBean.class);
    }

    @Override
    public ClienteDetalleDto toDto(ClienteDetalleBean detalleBean) {
        return modelMapper.map(detalleBean, ClienteDetalleDto.class);
    }

    public ClienteDetalleWithClienteDto toDetalleWithClienteDto(ClienteDetalleBean bean){
        ClienteDetalleBean detalleBean = (ClienteDetalleBean) bean;
        ClienteDto clienteDto = modelMapper.map(detalleBean.getCliente(), ClienteDto.class);
        ClienteDetalleWithClienteDto detalleDto = modelMapper.map(detalleBean, ClienteDetalleWithClienteDto.class);
        detalleDto.setCliente(clienteDto);
        return detalleDto;
    }

}