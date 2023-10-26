package com.example.sdfernandobrizuela.services;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
import com.example.sdfernandobrizuela.dtos.ClienteDetalleWithClienteDto;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.repositories.IClienteDetalleRepository;
import com.example.sdfernandobrizuela.repositories.IClienteRepository;
import com.example.sdfernandobrizuela.utils.mappers.clienteMapper.ClienteDetalleMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public ClienteDetalleDto create(ClienteDetalleDto clienteDetalleDto) {

        ClienteDetalleBean clienteDetalleBean = new ClienteDetalleBean();
        ClienteBean clienteBean = clienteRepository.getById(clienteDetalleDto.getCliente().getId());

        if(clienteBean!=null){
            clienteDetalleBean.setCliente(clienteBean);
        } else {
            ClienteBean nuevoCliente = new ClienteBean();
            nuevoCliente.setNombre(clienteDetalleDto.getCliente().getNombre());
            nuevoCliente.setRuc(clienteDetalleDto.getCliente().getRuc());
            nuevoCliente.setCedula(clienteDetalleDto.getCliente().getCedula());
            clienteDetalleBean.setCliente(clienteRepository.save(nuevoCliente));
        }

        clienteDetalleBean.setDireccion(clienteDetalleDto.getDireccion());
        clienteDetalleBean.setEmail(clienteDetalleDto.getEmail());
        clienteDetalleBean.setTelefono(clienteDetalleDto.getTelefono());

        return clienteDetalleMapper.toDto(clienteDetalleRepository.save(clienteDetalleBean));
    }

    @Override
    public Optional<ClienteDetalleDto> getById(Integer id) {
        ClienteDetalleBean clienteDetalleBean = clienteDetalleRepository.getById(id);
        return Optional.of(clienteDetalleMapper.toDetalleWithClienteDto(clienteDetalleBean));
    }

    public Optional<ClienteDetalleDto> getByUserId(Integer id){
        ClienteDetalleBean clienteDetalleBean = clienteDetalleRepository.findByClienteId(id);
        return Optional.of(clienteDetalleMapper.toDto(clienteDetalleBean));
    }

    @Override
    public List<ClienteDetalleDto> getAll(Pageable pag) {
        List<ClienteDetalleBean> clienteDetallesBean = clienteDetalleRepository.findAll();
        List<ClienteDetalleDto> clientesDetallesDto = new ArrayList<>();

        clienteDetallesBean.forEach(detalle ->
                clientesDetallesDto.add(clienteDetalleMapper.toDto(detalle))
        );
        return clientesDetallesDto;
    }

    @Override
    public Optional<ClienteDetalleDto> update(Integer id, ClienteDetalleDto clienteDetalleWithClienteDto) {
        Optional<ClienteDetalleBean> detalleOp = clienteDetalleRepository.findById(id);
        if(detalleOp.isPresent()){
            if(!clienteDetalleWithClienteDto.getEmail().isEmpty()) detalleOp.get().setEmail(clienteDetalleWithClienteDto.getEmail());
            if(!clienteDetalleWithClienteDto.getTelefono().isEmpty()) detalleOp.get().setTelefono(clienteDetalleWithClienteDto.getTelefono());
            if(!clienteDetalleWithClienteDto.getDireccion().isEmpty()) detalleOp.get().setDireccion(clienteDetalleWithClienteDto.getDireccion());
            clienteDetalleRepository.save(detalleOp.get());
            return Optional.ofNullable(clienteDetalleMapper.toDto(detalleOp.get()));
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try{
            clienteDetalleRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
