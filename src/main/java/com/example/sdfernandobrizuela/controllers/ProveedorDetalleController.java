package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.proveedor.ProveedorDetalleDto;
import com.example.sdfernandobrizuela.interfaces.IController;
import com.example.sdfernandobrizuela.interfaces.IService;
import com.example.sdfernandobrizuela.utils.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("proveedores-detalles")
public class ProveedorDetalleController implements IController<ProveedorDetalleDto> {


    @Autowired
    IService<ProveedorDetalleDto> proveedorDetalleService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity create(@RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        return ResponseEntity.ok(proveedorDetalleService.create(proveedorDetalleDto));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity getById(@PathVariable Integer id) {
        ProveedorDetalleDto detalleDto = proveedorDetalleService.getById(id);
        if (detalleDto != null) {
            return ResponseEntity.ok(detalleDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAll(@PathVariable(value = "page_num") Integer page) {
        Pageable pag = PageRequest.of(page - 1, Setting.PAGE_SIZE);
        Page proveedorDetalleDtoPage = proveedorDetalleService.getAll(pag);

        Map<String, Object> response = new HashMap<>();
        response.put("proveedores-detalles", proveedorDetalleDtoPage.getContent());
        response.put("currentPage", proveedorDetalleDtoPage.getNumber() + 1);
        response.put("totalItems", proveedorDetalleDtoPage.getTotalElements());
        response.put("totalPages", proveedorDetalleDtoPage.getTotalPages());

        return ResponseEntity.ok(response);

    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ProveedorDetalleDto proveedorDetalleDto) {
        ProveedorDetalleDto detalleDto = proveedorDetalleService.update(id, proveedorDetalleDto);
        if (detalleDto != null) {
            return ResponseEntity.ok(detalleDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (proveedorDetalleService.delete(id)) {
            return ResponseEntity.ok("Se ha eliminado el detalle " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido eliminar el detalle " + id);
    }

}
