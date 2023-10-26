package com.example.sdfernandobrizuela.utils.mappers.proveedorMapper;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import com.example.sdfernandobrizuela.dtos.ProveedorDto;
import com.example.sdfernandobrizuela.dtos.ProveedorWithDetalleDto;
import com.example.sdfernandobrizuela.utils.mappers.AbstractMapper;
import org.modelmapper.ModelMapper;

public class ProveedorMapper extends AbstractMapper<ProveedorBean, ProveedorDto> {
    @Override
    public ProveedorBean toBean(ProveedorDto dto) {
        return modelMapper.map(dto, ProveedorBean.class);
    }

    @Override
    public ProveedorDto toDto(ProveedorBean bean) {
        return modelMapper.map(bean, ProveedorDto.class);
    }

    public ProveedorWithDetalleDto toProveedorWithDetalleDto(ProveedorBean bean) {
        return modelMapper.map(bean, ProveedorWithDetalleDto.class);
    }

}