package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ClienteDto;
import com.example.sdfernandobrizuela.interfaces.IController;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController implements IController<ClienteDto> {

    @Autowired
    IService<ClienteDto> clienteService;

    @Override
    @PostMapping
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity create(@RequestBody ClienteDto clienteDto) {
        ClienteDto cliente = clienteService.create(clienteDto);
        return ResponseEntity.ok(cliente);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize( "hasRole('USER')")
    public ResponseEntity getById(@PathVariable Integer id) {
        ClienteDto cliente = clienteService.getById(id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente con id: " + id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    @PreAuthorize( "hasRole('USER')")
    public List<ClienteDto> getAll(@PathVariable(value = "page_num") Integer page) {
        Pageable pag = PageRequest.of(page - 1, Setting.PAGE_SIZE);
        return clienteService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto) {
        ClienteDto cliente = clienteService.update(id, clienteDto);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente con id: " + id);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (clienteService.delete(id)) {
            return ResponseEntity.ok("Se ha eliminado al usuario con id " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente con id: " + id);
    }
}
