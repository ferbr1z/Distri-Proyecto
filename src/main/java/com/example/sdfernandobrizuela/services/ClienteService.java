package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteDetalleMapper;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IService<ClienteDto> {

    @Autowired
    IClienteRepository clienteRepository;
    @Autowired
    IClienteDetalleRepository clienteDetalleRepository;
    private ClienteMapper clienteMapper = new ClienteMapper();
    private ClienteDetalleMapper clienteDetalleMapper = new ClienteDetalleMapper();

    @Override
    public ClienteDto create(ClienteDto clienteDto) {
        ClienteBean cliente = clienteMapper.toBean(clienteDto);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDto getById(Integer id) {
        Optional<ClienteBean> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            ClienteDto clienteDto = clienteMapper.toDto(cliente.get()); // Se obtiene el detalle y se lo asigna al dto
            ClienteDetalleBean detalleBean = clienteDetalleRepository.findByClienteId(cliente.get().getId()); // si hay un detalle, tambien se envia
            if (detalleBean != null) clienteDto.setClienteDetalleId((detalleBean.getId()));
            return clienteDto;
        } else {
            return null;
        }
    }

    @Override
    public List<ClienteDto> getAll(Pageable pag) {
        List<ClienteBean> clientes = clienteRepository.findAll();
        List<ClienteDto> clientesDto = new ArrayList<>();

        clientes.forEach(cliente -> {
                    ClienteDto clienteDto = clienteMapper.toDto(cliente);
                    ClienteDetalleBean detalleBean = clienteDetalleRepository.findByClienteId(cliente.getId());
                    if (detalleBean != null) clienteDto.setClienteDetalleId((detalleBean.getId()));
                    clientesDto.add(clienteDto);
                }

        );
        return clientesDto;
    }

    @Override
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
    public boolean delete(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
