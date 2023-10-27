package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ProveedorDto;
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
@RequestMapping("/proveedores")
public class ProveedorController implements IController<ProveedorDto> {
    @Autowired
    IService<ProveedorDto> proveedorService;
    @Override
    @PostMapping
    public ProveedorDto create(@RequestBody ProveedorDto proveedorDto) {
        return proveedorService.create(proveedorDto);
    }

    @Override
    @GetMapping("/{id}")
    public Optional<ProveedorDto> getById(@PathVariable Integer id) {
        return proveedorService.getById(id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    public List<ProveedorDto> getAll(@PathVariable(value = "page_num") Integer page){
        Pageable pag = PageRequest.of(page, Setting.PAGE_SIZE);
        return proveedorService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    public Optional<ProveedorDto> update(@PathVariable Integer id, @RequestBody ProveedorDto proveedorDto) {
        return proveedorService.update(id, proveedorDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        if(proveedorService.delete(id)){
            return "Se ha eliminado al usuario con id " + id;
        }
        return "No se ha podido eliminar al usuario con id " + id;    }
}
