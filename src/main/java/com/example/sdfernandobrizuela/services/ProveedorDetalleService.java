package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import com.example.sdfernandobrizuela.dtos.proveedor.ProveedorDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IProveedorDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IProveedorRepository;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorDetalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorDetalleService implements IService<ProveedorDetalleDto> {
    @Autowired
    private IProveedorDetalleRepository proveedorDetalleRepository;
    @Autowired
    private IProveedorRepository proveedorRepository;
    @Autowired
    private CacheManager cacheManager;
    private Logger logger = LoggerFactory.getLogger(ProveedorDetalleService.class);
    private ProveedorDetalleMapper proveedorDetalleMapper = new ProveedorDetalleMapper();

    @Override
    @Transactional
    public ProveedorDetalleDto create(ProveedorDetalleDto proveedorDetalleDto) {
        ProveedorDetalleBean proveedorDetalleBean = new ProveedorDetalleBean();
        ProveedorBean proveedorBean = proveedorRepository.getById(proveedorDetalleDto.getProveedorId());
        if (proveedorBean != null) {
            proveedorDetalleBean.setProveedor(proveedorBean);
        }

        proveedorBean.setActive(true);
        proveedorDetalleBean.setDireccion(proveedorDetalleDto.getDireccion());
        proveedorDetalleBean.setEmail(proveedorDetalleDto.getEmail());
        proveedorDetalleBean.setTelefono(proveedorDetalleDto.getTelefono());

        return proveedorDetalleMapper.toDto(proveedorDetalleRepository.save(proveedorDetalleBean));
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "sd::proveedorDetalleItem", key = "#id", unless = "#result==null")
    public ProveedorDetalleDto getById(Integer id) {
        Optional<ProveedorDetalleBean> proveedorDetalleBean = proveedorDetalleRepository.findByIdAndActiveTrue(id);
        return proveedorDetalleMapper.toDto(proveedorDetalleBean.get());
    }

    @Transactional
    @Cacheable(cacheNames = "sd::proveedorDetalleItem", key = "#id", unless = "#result==null")
    public ProveedorDetalleDto getByProveedorId(Integer id) {
        Optional<ProveedorDetalleBean> proveedorDetalleBean = proveedorDetalleRepository.findByIdAndActiveTrue(id);
        if(proveedorDetalleBean.isPresent()) {
            return proveedorDetalleMapper.toDto(proveedorDetalleBean.get());
        }
        return null;
    }


    @Override
    @Transactional
    public Page<ProveedorDetalleDto> getAll(Pageable pag) {
        Page<ProveedorDetalleBean> proveedorDetallesBean = proveedorDetalleRepository.findAll(pag);

        Page<ProveedorDetalleDto> proveedoresDetallesDto = proveedorDetallesBean.map(proveedorDetalleMapper::toDto);

        proveedoresDetallesDto.forEach(proveedorDetalleDto -> {
                    String cacheKey = "sd::proveedorDetalleItem::" + proveedorDetalleDto.getId();
                    cacheManager.getCache("sd").putIfAbsent(cacheKey, proveedorDetalleDto);
                }
        );

        return proveedoresDetallesDto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "sd::proveedorDetalleItem", key = "#id")
    public ProveedorDetalleDto update(Integer id, ProveedorDetalleDto proveedorDetalleDto) {
        Optional<ProveedorDetalleBean> detalleOp = proveedorDetalleRepository.findById(id);
        if (detalleOp.isPresent()) {
            ProveedorDetalleBean detalleBean = detalleOp.get();
            if (!proveedorDetalleDto.getDireccion().isEmpty())
                detalleBean.setDireccion(proveedorDetalleDto.getDireccion());
            if (!proveedorDetalleDto.getEmail().isEmpty()) detalleBean.setEmail(proveedorDetalleDto.getEmail());
            if (!proveedorDetalleDto.getTelefono().isEmpty()) detalleBean.setEmail(proveedorDetalleDto.getEmail());
            proveedorDetalleRepository.save(detalleBean);
            return proveedorDetalleMapper.toDto(detalleBean);
        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "sd::proveedorDetalleItem", key = "#id")
    public boolean delete(Integer id) {
        if (proveedorRepository.findByIdAndActiveTrue(id).isPresent()) {
            ProveedorDetalleBean proveedorDetalleBean = proveedorDetalleRepository.findByIdAndActiveTrue(id).get();
            proveedorDetalleBean.setActive(false);
            proveedorDetalleRepository.save(proveedorDetalleBean);
            return true;
        }
        return false;
    }
}
