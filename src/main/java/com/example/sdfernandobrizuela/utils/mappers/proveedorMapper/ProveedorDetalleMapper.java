package com.example.sdfernandobrizuela.utils.mappers.proveedorMapper;

import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import com.example.sdfernandobrizuela.dtos.ProveedorDetalleWithProveedorDto;
import com.example.sdfernandobrizuela.dtos.ProveedorDetalleDto;
import com.example.sdfernandobrizuela.dtos.ProveedorDto;
import com.example.sdfernandobrizuela.utils.mappers.AbstractMapper;

public class ProveedorDetalleMapper extends AbstractMapper<ProveedorDetalleBean, ProveedorDetalleDto> {

    @Override
    public ProveedorDetalleBean toBean(ProveedorDetalleDto dto) {
        return modelMapper.map(dto, ProveedorDetalleBean.class);
    }

    @Override
    public ProveedorDetalleDto toDto(ProveedorDetalleBean bean) {
        return modelMapper.map(bean, ProveedorDetalleDto.class);
    }

    public ProveedorDetalleWithProveedorDto toDetalleWithProveedorDto (ProveedorDetalleBean bean){
        ProveedorDetalleBean detalleBean = bean;
        ProveedorDto proveedorDto = modelMapper.map(detalleBean.getProveedor(), ProveedorDto.class);
        ProveedorDetalleWithProveedorDto detalleDto = modelMapper.map(detalleBean, ProveedorDetalleWithProveedorDto.class);
        detalleDto.setProveedor(proveedorDto);
        return detalleDto;
    }

}
