package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import com.example.sdfernandobrizuela.dtos.ProveedorDetalleWithProveedorDto;
import com.example.sdfernandobrizuela.dtos.ProveedorDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IProveedorDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IProveedorRepository;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorDetalleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorDetalleService implements IService<ProveedorDetalleDto> {
    @Autowired
    private IProveedorDetalleRepository proveedorDetalleRepository;
    @Autowired
    private IProveedorRepository proveedorRepository;
    private ProveedorDetalleMapper proveedorDetalleMapper = new ProveedorDetalleMapper();

    @Override
    public ProveedorDetalleDto create(ProveedorDetalleDto proveedorDetalleDto) {
        return (ProveedorDetalleWithProveedorDto) proveedorDetalleDto;
    }

    @Override
    public Optional<ProveedorDetalleDto> getById(Integer id) {
        ProveedorDetalleBean proveedorDetalleBean = proveedorDetalleRepository.findByProveedorId(id);
        return Optional.of(proveedorDetalleMapper.toDto(proveedorDetalleBean));
    }

    @Override
    public List<ProveedorDetalleDto> getAll(Pageable pag) {
        List<ProveedorDetalleBean> proveedorDetallesBean = proveedorDetalleRepository.findAll();
        List<ProveedorDetalleDto> proveedoresDetallesDto = new ArrayList<>();

        proveedorDetallesBean.forEach(detalle ->
                proveedoresDetallesDto.add(proveedorDetalleMapper.toDto(detalle))
        );
        return proveedoresDetallesDto;
    }

    @Override
    public Optional<ProveedorDetalleDto> update(Integer id, ProveedorDetalleDto proveedorDetalleDto) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
