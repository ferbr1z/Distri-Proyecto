package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.interfaces.IController;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController implements IController<ClienteDto> {

    @Autowired
    IService<ClienteDto> clienteService;

    @Override
    @PostMapping
    public ClienteDto create(@RequestBody ClienteDto clienteDto) {
        return clienteService.create(clienteDto);
    }

    @Override
    @GetMapping("/{id}")
    public Optional<ClienteDto> getById(@PathVariable Integer id) {
        return clienteService.getById(id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    public List<ClienteDto> getAll(@PathVariable(value = "page_num") Integer page){
        Pageable pag = PageRequest.of(page, Setting.PAGE_SIZE);
        return clienteService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    public Optional<ClienteDto> update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto) {
        return clienteService.update(id, clienteDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if(clienteService.delete(id)){
            return "Se ha eliminado al usuario con id " + id;
        }
        return "No se ha podido eliminar al usuario con id " + id;
    }
}
