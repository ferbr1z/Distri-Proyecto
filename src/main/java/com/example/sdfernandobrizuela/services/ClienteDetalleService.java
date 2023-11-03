package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
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
    @Cacheable(cacheNames = "sd::clienteDetalleItem", key = "#id", unless = "#result==null")
    public ClienteDetalleDto getById(Integer id) {
        Optional<ClienteDetalleBean> clienteDetalleOp = clienteDetalleRepository.findById(id);
        return clienteDetalleOp.map(clienteDetalleBean -> clienteDetalleMapper.toDto(clienteDetalleBean)).orElse(null);
    }

    @Override
    public List<ClienteDetalleDto> getAll(Pageable pag) {
        Page<ClienteDetalleBean> clienteDetallesBean = clienteDetalleRepository.findAll(pag);
        List<ClienteDetalleDto> clientesDetallesDto = new ArrayList<>();

        clienteDetallesBean.forEach(detalle ->
                {
                    ClienteDetalleDto clienteDetalleDto = clienteDetalleMapper.toDto(detalle);
                    // Cacheamos cada DTO
                    String cacheKey = "clienteDetalleItem::" + clienteDetalleDto.getId();
                    Cache cache = cacheManager.getCache("sd");
                    Object elementoCacheado = cache.get(cacheKey, Object.class);

                    if (elementoCacheado == null) {
                        logger.info("Cacheando clienteDetalle con id: " + clienteDetalleDto.getId());
                        cache.put(cacheKey, clienteDetalleDto);
                    }

                    clientesDetallesDto.add(clienteDetalleDto);
                }
        );
        return clientesDetallesDto;
    }

    @Override
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
