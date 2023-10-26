package com.example.sdfernandobrizuela.utils.mappers;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.ClienteWithDetailDto;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import org.modelmapper.ModelMapper;

public class ClienteMapper extends AbstractMapper<ClienteBean, ClienteDto> {
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ClienteBean toBean(ClienteDto dto) {
        return modelMapper.map(dto, ClienteBean.class);
    }

    @Override
    public ClienteDto toDto(ClienteBean bean) {
        return modelMapper.map(bean, ClienteDto.class);
    }

    public ClienteWithDetailDto toClienteWithDetailDto(ClienteBean bean){
        return modelMapper.map(bean, ClienteWithDetailDto.class);
    }

}
