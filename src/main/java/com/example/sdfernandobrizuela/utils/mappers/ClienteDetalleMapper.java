package com.example.sdfernandobrizuela.utils.mappers;

import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleWithClienteDto;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import org.modelmapper.ModelMapper;

public class ClienteDetalleMapper extends AbstractMapper<ClienteDetalleBean, ClienteDetalleDto> {
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public ClienteDetalleBean toBean(ClienteDetalleDto dto) {
        return modelMapper.map(dto, ClienteDetalleBean.class);
    }

    @Override
    public ClienteDetalleDto toDto(ClienteDetalleBean detalleBean) {
        return modelMapper.map(detalleBean, ClienteDetalleDto.class);
    }

    public ClienteDetalleWithClienteDto toClienteDetalleDto(ClienteDetalleBean bean){
        ClienteDetalleBean detalleBean = (ClienteDetalleBean) bean;
        ClienteDto clienteDto = modelMapper.map(detalleBean.getCliente(), ClienteDto.class);
        ClienteDetalleWithClienteDto detalleDto = modelMapper.map(detalleBean, ClienteDetalleWithClienteDto.class);
        detalleDto.setCliente(clienteDto);
        return detalleDto;
    }

}
