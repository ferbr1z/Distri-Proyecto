package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.interfaces.IBean;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService implements IService<ClienteDto> {

    @Autowired
    IClienteRepository clienteRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public ClienteDto getById(Integer id) {
        ClienteBean cliente = clienteRepository.findById(id).get();
        return toDto(cliente);
    }

    @Override
    public List<ClienteDto> getAll(Pageable pag) {
        List<ClienteBean> clientes =clienteRepository.findAll();
        List<ClienteDto> clientesDto = new ArrayList<>();

        clientes.forEach(cliente ->
                clientesDto.add(toDto(cliente))
        );
        return clientesDto;
    }

    @Override
    public ClienteDto update(Integer id, ClienteDto clienteBean) {
        ClienteBean cliente = clienteRepository.findById(id).get();

        if(clienteBean.getCedula() != null) cliente.setCedula(clienteBean.getCedula());
        if(clienteBean.getNombre() != null) cliente.setNombre(clienteBean.getNombre());
        if(clienteBean.getRuc() != null) cliente.setRuc(clienteBean.getRuc());
        if(clienteBean.getEmail() != null) cliente.setEmail(clienteBean.getEmail());
        if(clienteBean.getTelefono() != null) cliente.setTelefono(clienteBean.getTelefono());

        clienteRepository.save(cliente);

        return toDto(cliente);
    }

    @Override
    public boolean delete(Integer id) {
        try{
            clienteRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ClienteDto toDto(IBean clienteBean){
        return modelMapper.map(clienteBean, ClienteDto.class);
    }

}
