package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ClienteDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IController;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes-detalles")
public class ClienteDetalleController implements IController<ClienteDetalleDto> {

    @Autowired
    private IService<ClienteDetalleDto> clienteDetalleService;

    @Override
    @PostMapping
    public ClienteDetalleDto create(@RequestBody ClienteDetalleDto clienteDetalleDto) {
        return clienteDetalleService.create(clienteDetalleDto);
    }

    @Override
    @GetMapping("/{id}")
    public Optional<ClienteDetalleDto> getById(@PathVariable Integer id) {
        return clienteDetalleService.getById(id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    public List<ClienteDetalleDto> getAll(@PathVariable(value = "page_num") Integer page){
        Pageable pag = PageRequest.of(page, Setting.PAGE_SIZE);
        return clienteDetalleService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    public Optional<ClienteDetalleDto> update(@PathVariable Integer id, @RequestBody ClienteDetalleDto clienteDetalleWithClienteDto) {
        return clienteDetalleService.update(id, clienteDetalleWithClienteDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if(clienteDetalleService.delete(id)){
            return "Se ha borrado el detalle de cliente " + id;
        }
        return "No se ha podido borrar el detalle de cliente " + id;
    }
}
