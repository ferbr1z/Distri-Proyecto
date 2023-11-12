package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import com.example.sdfernandobrizuela.dtos.ProveedorDto;
import com.example.sdfernandobrizuela.dtos.ProveedorWithDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IProveedorDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IProveedorRepository;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorDetalleMapper;
import com.example.sdfernandobrizuela.utils.mappers.proveedorMapper.ProveedorMapper;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProveedorService implements IService<ProveedorDto> {
    @Autowired
    private IProveedorRepository proveedorRepository;
    @Autowired
    private ProveedorDetalleService proveedorDetalleService;
    @Autowired
    private CacheManager cacheManager;
    private Logger logger = LoggerFactory.getLogger(ProveedorService.class);
    private ProveedorMapper proveedorMapper = new ProveedorMapper();
    private ProveedorDetalleMapper proveedorDetalleMapper = new ProveedorDetalleMapper();

    @Override
    @Transactional
    public ProveedorDto create(ProveedorDto proveedorDto) {
        ProveedorBean proveedor = proveedorMapper.toBean(proveedorDto);
        return proveedorMapper.toDto(proveedorRepository.save(proveedor));
    }

    @Override
    @Cacheable(cacheNames = "sd::proveedorWithDetalleItem", key = "#id", unless = "#result==null")
    @Transactional
    public ProveedorDto getById(Integer id) {
        Optional<ProveedorBean> proveedor = proveedorRepository.findByIdAndActiveTrue(id);
        if (proveedor.isPresent()) {
            ProveedorDto proveedorDto = proveedorMapper.toDto(proveedor.get());
            ProveedorWithDetalleDto proveedorWithDetalleDto = new ProveedorWithDetalleDto();
            proveedorWithDetalleDto.setId(proveedorDto.getId());
            proveedorWithDetalleDto.setNombre(proveedorDto.getNombre());
            proveedorWithDetalleDto.setRuc(proveedorDto.getRuc());
            proveedorWithDetalleDto.setProveedorDetalle(proveedorDetalleService.getByProveedorId(proveedorDto.getId()));
            return proveedorWithDetalleDto;
        }
        return null;
    }


    @Override
    @Transactional
    public List<ProveedorDto> getAll(Pageable pag) {
        Page<ProveedorBean> proveedores = proveedorRepository.findAll(pag);
        List<ProveedorDto> proveedoresDto = new ArrayList<>();

        proveedores.forEach(proveedor ->
                {
                    ProveedorDto proveedorDto = proveedorMapper.toDto(proveedor);
                    // Cacheamos cada DTO
                    String cacheKey = "proveedorItem::" + proveedorDto.getId();
                    Cache cache = cacheManager.getCache("sd");
                    Object elementoCacheado = cache.get(cacheKey, Object.class);

                    if (elementoCacheado == null) {
                        logger.info("Cacheando proveedor con id: " + proveedorDto.getId());
                        cache.put(cacheKey, proveedorDto);
                    }
                    proveedoresDto.add(proveedorDto);
                }
        );
        return proveedoresDto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "sd::proveedorItem", key = "#id")
    public ProveedorDto update(Integer id, ProveedorDto proveedorDto) {
        Optional<ProveedorBean> proveedorBean = proveedorRepository.findById(id);
        if (proveedorBean.isPresent()) {
            if (proveedorDto.getNombre() != null) proveedorBean.get().setNombre(proveedorDto.getNombre());
            if (proveedorDto.getRuc() != null) proveedorBean.get().setRuc(proveedorDto.getRuc());
            proveedorRepository.save(proveedorBean.get());
            return proveedorMapper.toDto(proveedorBean.get());

        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "sd::proveedorItem", key = "#id")
    public boolean delete(Integer id) {
        if (proveedorRepository.findByIdAndActiveTrue(id).isPresent()) {
            ProveedorBean proveedorBean = proveedorRepository.getById(id);
            proveedorBean.setActive(false);
            proveedorRepository.save(proveedorBean);
            return true;
        } else {
            throw new NoSuchElementException("No se ha encontrado el proveedor con id: " + id);
        }
    }
}
