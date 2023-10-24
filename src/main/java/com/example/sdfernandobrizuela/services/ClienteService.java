package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
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

    @Override
    public ClienteDto getById(Integer id) {
        ClienteBean cliente = clienteRepository.findById(id).get();
        return new ClienteDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCedula(),
                cliente.getTelefono(),
                cliente.getEmail());
    }

    @Override
    public List<ClienteDto> getAll(Pageable pag) {
        List<ClienteBean> clientes =clienteRepository.findAll();
        List<ClienteDto> clientesDto = new ArrayList<>();

        clientes.forEach(cliente ->
                clientesDto.add(new ClienteDto(
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getCedula(),
                        cliente.getTelefono(),
                        cliente.getEmail()))
        );
        return clientesDto;
    }

    @Override
    public ClienteDto update(Integer id, ClienteDto clienteBean) {
        ClienteBean cliente = clienteRepository.findById(id).get();

        cliente.setCedula(clienteBean.getCedula());
        cliente.setNombre(clienteBean.getNombre());
        cliente.setEmail(clienteBean.getEmail());
        cliente.setTelefono(clienteBean.getTelefono());

        return new ClienteDto(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getCedula(),
                cliente.getTelefono(),
                cliente.getEmail());
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
}
