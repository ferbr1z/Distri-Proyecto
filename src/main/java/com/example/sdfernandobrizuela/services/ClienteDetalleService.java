package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.cliente.ClienteDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import com.example.sdfernandobrizuela.utils.CacheConfig;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteDetalleMapper;
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
public class ClienteDetalleService implements IService<ClienteDetalleDto> {
    @Autowired
    private IClienteDetalleRepository clienteDetalleRepository;
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private CacheManager cacheManager;
    private Logger logger = LoggerFactory.getLogger(ClienteDetalleService.class);

    private ClienteDetalleMapper clienteDetalleMapper = new ClienteDetalleMapper();

    private CacheConfig cacheConfig;

    @Autowired
    public ClienteDetalleService(CacheConfig cacheConfig){
        this.cacheConfig = cacheConfig;
    }

    @Override
    @Transactional
    public ClienteDetalleDto create(ClienteDetalleDto clienteDetalleDto) {

        ClienteDetalleBean clienteDetalleBean = new ClienteDetalleBean();
        ClienteBean clienteBean = clienteRepository.getById(clienteDetalleDto.getClienteId());

        if (clienteBean != null) {
            clienteDetalleBean.setCliente(clienteBean);
        }

        clienteDetalleBean.setDireccion(clienteDetalleDto.getDireccion());
        clienteDetalleBean.setEmail(clienteDetalleDto.getEmail());
        clienteDetalleBean.setTelefono(clienteDetalleDto.getTelefono());

        ClienteDetalleDto nuevoCliente = clienteDetalleMapper.toDto(clienteDetalleRepository.save(clienteDetalleBean));
        return nuevoCliente;
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "sd::clienteDetalleItem", key = "#id", unless = "#result==null")
    public ClienteDetalleDto getById(Integer id) {
        Optional<ClienteDetalleBean> clienteDetalleOp = clienteDetalleRepository.findById(id);
        return clienteDetalleOp.map(clienteDetalleBean -> clienteDetalleMapper.toDto(clienteDetalleBean)).orElse(null);
    }

    public ClienteDetalleDto getByClienteId(Integer id) {
        Optional<ClienteDetalleBean> clienteDetalleBean = clienteDetalleRepository.findByClienteId(id);
        if(clienteDetalleBean.isPresent()){
            return clienteDetalleMapper.toDto(clienteDetalleBean.get());
        }
        return null;
    }

    @Override
    @Transactional
    public Page<ClienteDetalleDto> getAll(Pageable pag) {
        Page<ClienteDetalleBean> clienteDetallesBean = clienteDetalleRepository.findAll(pag);
        Page<ClienteDetalleDto> clienteDetallesDto = clienteDetallesBean.map(clienteDetalleMapper::toDto);

        clienteDetallesDto.forEach(clienteDetalleDto -> {
                    String cacheKey = "sd::clienteDetalleItem::" + clienteDetalleDto.getId();
                    cacheManager.getCache("sd").putIfAbsent(cacheKey, clienteDetalleDto);
                }
        );

        return clienteDetallesDto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "sd::clienteDetalleItem", key = "#id")
    public ClienteDetalleDto update(Integer id, ClienteDetalleDto clienteDetalleWithClienteDto) {
        Optional<ClienteDetalleBean> detalleOp = clienteDetalleRepository.findById(id);
        if (detalleOp.isPresent()) {
            if (clienteDetalleWithClienteDto.getEmail() != null)
                detalleOp.get().setEmail(clienteDetalleWithClienteDto.getEmail());
            if (clienteDetalleWithClienteDto.getTelefono() != null)
                detalleOp.get().setTelefono(clienteDetalleWithClienteDto.getTelefono());
            if (clienteDetalleWithClienteDto.getDireccion() != null)
                detalleOp.get().setDireccion(clienteDetalleWithClienteDto.getDireccion());
            clienteDetalleRepository.save(detalleOp.get());
            return clienteDetalleMapper.toDto(detalleOp.get());
        }
        return null;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "sd::clienteDetalleItem", key="#id")
    public boolean delete(Integer id) {
        if (clienteDetalleRepository.existsById(id)) {
            clienteDetalleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
