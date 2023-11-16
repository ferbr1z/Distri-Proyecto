package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.cliente.ClienteDto;
import com.example.sdfernandobrizuela.dtos.cliente.ClienteWithDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteDetalleMapper;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService implements IService<ClienteDto> {

    @Autowired
    IClienteRepository clienteRepository;
    @Autowired
    ClienteDetalleService clienteDetalleService;
    @Autowired
    private CacheManager cacheManager;
    private Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private ClienteMapper clienteMapper = new ClienteMapper();
    private ClienteDetalleMapper clienteDetalleMapper = new ClienteDetalleMapper();


    @Override
    @Transactional
    public ClienteDto create(ClienteDto clienteDto) {
        ClienteBean cliente = clienteMapper.toBean(clienteDto);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "sd::clienteItem", key = "#id", unless = "#result==null")
    public ClienteDto getById(Integer id) {
        Optional<ClienteBean> cliente = clienteRepository.findByIdAndActiveTrue(id);
        if (cliente.isPresent()) {
            ClienteDto clienteDto = clienteMapper.toDto(cliente.get()); // Se obtiene el detalle y se lo asigna al dto
            ClienteWithDetalleDto clienteWithDetalleDto = new ClienteWithDetalleDto();
            clienteWithDetalleDto.setCedula(clienteDto.getCedula());
            clienteWithDetalleDto.setId(clienteDto.getId());
            clienteWithDetalleDto.setNombre(clienteDto.getNombre());
            clienteWithDetalleDto.setRuc(clienteDto.getRuc());

            // se obtiene el detalle del cliente desde el servicio de detalle
            clienteWithDetalleDto.setClienteDetalle(
                    clienteDetalleService.getByClienteId(clienteDto.getId()));
            return clienteWithDetalleDto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Page<ClienteDto> getAll(Pageable pag) {
        Page<ClienteBean> clientesBean = clienteRepository.findAll(pag);
        Page<ClienteDto> clientesDto = clientesBean.map(clienteMapper::toDto);

        // cacheamos
        clientesDto.forEach(cliente -> {
                    String cacheKey = "clienteItem::" + cliente.getId();
                    cacheManager.getCache("sd").putIfAbsent(cacheKey, cliente);
                }
        );

        return clientesDto;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "sd::clienteItem", key = "#id")
    public ClienteDto update(Integer id, ClienteDto clienteDto) {
        Optional<ClienteBean> clienteOp = clienteRepository.findById(id);

        if (clienteOp.isPresent()) {
            if (clienteDto.getNombre() != null) clienteOp.get().setNombre(clienteDto.getNombre());
            if (clienteDto.getRuc() != null) clienteOp.get().setRuc(clienteDto.getRuc());
            if (clienteDto.getCedula() != null) clienteOp.get().setCedula(clienteDto.getCedula());
            clienteRepository.save(clienteOp.get());
            return clienteMapper.toDto(clienteOp.get());
        }

        return null;

    }

    @Override
    @CacheEvict(cacheNames = "sd::clienteItem", key = "#id")
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean delete(Integer id) {
        if (clienteRepository.findByIdAndActiveTrue(id).isPresent()) {
            ClienteBean clienteBean = clienteRepository.getById(id);
            clienteBean.setActive(false);
            clienteRepository.save(clienteBean);
            return true;
        } else {
            throw new NoSuchElementException("No se ha encontrado el cliente con id: " + id);
        }
    }

}
