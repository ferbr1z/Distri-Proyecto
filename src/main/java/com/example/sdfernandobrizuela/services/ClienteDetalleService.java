package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import com.example.sdfernandobrizuela.utils.CacheConfig;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteDetalleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "clienteDetalle", key = "#id")
    public ClienteDetalleDto getById(Integer id) {
        Optional<ClienteDetalleBean> clienteDetalleOp = clienteDetalleRepository.findById(id);
        return clienteDetalleOp.map(clienteDetalleBean -> clienteDetalleMapper.toDto(clienteDetalleBean)).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "clientesDetalles")
    public List<ClienteDetalleDto> getAll(Pageable pag) {
        List<ClienteDetalleBean> clienteDetallesBean = clienteDetalleRepository.findAll();
        List<ClienteDetalleDto> clientesDetallesDto = new ArrayList<>();

        clienteDetallesBean.forEach(detalle ->
                clientesDetallesDto.add(clienteDetalleMapper.toDto(detalle))
        );
        return clientesDetallesDto;
    }

    @Override
    @CachePut(cacheNames = "clienteDetalle", key = "#id")
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
    @CacheEvict(cacheNames = "clienteDetalle", key="#id")
    public boolean delete(Integer id) {
        if (clienteDetalleRepository.existsById(id)) {
            clienteDetalleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
