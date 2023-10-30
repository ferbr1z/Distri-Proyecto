package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.ProveedorDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IController;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("proveedores-detalles")
public class ProveedorDetalleController implements IController<ProveedorDetalleDto> {


    @Autowired
    IService<ProveedorDetalleDto> proveedorDetalleService;

    @Override
    @PostMapping
    public ResponseEntity create(@RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        return ResponseEntity.ok(proveedorDetalleService.create(proveedorDetalleDto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id) {
        ProveedorDetalleDto detalleDto = proveedorDetalleService.getById(id);
        if(detalleDto!=null){
            return ResponseEntity.ok(detalleDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    public List<ProveedorDetalleDto> getAll(@PathVariable(value = "page_num") Integer page) {
        Pageable pag = PageRequest.of(page, Setting.PAGE_SIZE);
        return proveedorDetalleService.getAll(pag);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        ProveedorDetalleDto detalleDto = proveedorDetalleService.update(id, proveedorDetalleDto);
        if(detalleDto != null){
            return ResponseEntity.ok(detalleDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        if(proveedorDetalleService.delete(id)){
            return ResponseEntity.ok("Se ha eliminado el detalle " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido eliminar el detalle " + id);
    }

}
