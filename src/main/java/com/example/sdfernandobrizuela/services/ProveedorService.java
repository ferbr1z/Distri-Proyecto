package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import com.example.sdfernandobrizuela.dtos.ProveedorDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IProveedorDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IProveedorRepository;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorDetalleMapper;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IService<ProveedorDto> {
    @Autowired
    private IProveedorRepository proveedorRepository;
    @Autowired
    private IProveedorDetalleRepository proveedorDetalleRepository;
    private ProveedorMapper proveedorMapper = new ProveedorMapper();
    private ProveedorDetalleMapper proveedorDetalleMapper = new ProveedorDetalleMapper();
    @Override
    public ProveedorDto create(ProveedorDto proveedorDto) {
        ProveedorBean proveedor = proveedorMapper.toBean(proveedorDto);
        return proveedorMapper.toDto(proveedorRepository.save(proveedor));
    }

    @Override
    public Optional<ProveedorDto> getById(Integer id) {
        Optional<ProveedorBean> proveedor = proveedorRepository.findById(id);
        if(proveedor.isPresent()){
            ProveedorDto proveedorDto = proveedorMapper.toDto(proveedor.get());
            // Se obtiene el detalle y se lo asigna al dto
            ProveedorDetalleBean detalleBean = proveedorDetalleRepository.findByProveedorId(proveedor.get().getId());

            if(detalleBean!=null)  proveedorDto.setProveedorDetalleId (detalleBean.getId());

            return Optional.of(proveedorDto);
        }
        return Optional.empty();
    }

    @Override
    public List<ProveedorDto> getAll(Pageable pag) {
        Page<ProveedorBean> proveedores = proveedorRepository.findAll(pag);
        List<ProveedorDto> proveedoresDto = new ArrayList<>();

        proveedores.forEach(proveedor ->
                {
                    ProveedorDto proveedorDto = proveedorMapper.toDto(proveedor);
                    ProveedorDetalleBean detalleBean = proveedorDetalleRepository.findByProveedorId(proveedor.getId());
                    if(detalleBean!=null)  proveedorDto.setProveedorDetalleId (detalleBean.getId());
                    proveedoresDto.add(proveedorDto);
                }
        );

        return proveedoresDto;
    }

    @Override
    public Optional<ProveedorDto> update(Integer id, ProveedorDto proveedorDto) {
        Optional<ProveedorBean> proveedorBean = proveedorRepository.findById(id);
        if(proveedorBean.isPresent()){
            if(proveedorDto.getNombre()!=null) proveedorBean.get().setNombre(proveedorDto.getNombre());
            if(proveedorDto.getRuc()!=null) proveedorBean.get().setRuc(proveedorDto.getRuc());
            proveedorRepository.save(proveedorBean.get());
            return Optional.of(proveedorMapper.toDto(proveedorBean.get()));

        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        try{
            proveedorRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
