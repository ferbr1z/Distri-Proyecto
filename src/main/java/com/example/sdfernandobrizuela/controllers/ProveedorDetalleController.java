package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ProveedorDetalleDto;
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
@RequestMapping("proveedores-detalles")
public class ProveedorDetalleController implements IController<ProveedorDetalleDto> {


    @Autowired
    IService<ProveedorDetalleDto> proveedorDetalleService;

    @Override
    @PostMapping
    public ProveedorDetalleDto create(@RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        return proveedorDetalleService.create(proveedorDetalleDto);
    }

    @Override
    @GetMapping("/{id}")
    public Optional<ProveedorDetalleDto> getById(@PathVariable Integer id) {
        return proveedorDetalleService.getById(id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    public List<ProveedorDetalleDto> getAll(@PathVariable(value = "page_num") Integer page) {
        Pageable pag = PageRequest.of(page, Setting.PAGE_SIZE);
        return proveedorDetalleService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    public Optional<ProveedorDetalleDto> update(@PathVariable Integer id, @RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        return proveedorDetalleService.update(id, proveedorDetalleDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if(proveedorDetalleService.delete(id)){
            return "Se ha eliminado el detalle " + id;
        }
        return "No se ha podido eliminar el detalle " + id;
    }

}
