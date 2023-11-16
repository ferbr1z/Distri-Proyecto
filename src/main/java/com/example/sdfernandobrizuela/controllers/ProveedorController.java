package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.proveedor.ProveedorDto;
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
import java.util.Map;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController implements IController<ProveedorDto> {
    @Autowired
    IService<ProveedorDto> proveedorService;

    @Override
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity create(@RequestBody ProveedorDto proveedorDto) {
        return ResponseEntity.ok(proveedorService.create(proveedorDto));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getById(@PathVariable Integer id) {
        ProveedorDto proveedorDto = proveedorService.getById(id);
        if (proveedorDto != null) {
            return ResponseEntity.ok(proveedorDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente con id: " + id);
    }

    @Override
    @GetMapping("/pages/{page_num}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> getAll(@PathVariable(value = "page_num") Integer page) {
        Pageable pag = PageRequest.of(page - 1, Setting.PAGE_SIZE);

        Page proveedorDtoPage = proveedorService.getAll(pag);

        Map<String, Object> response = new HashMap<>();
        response.put("proveedores", proveedorDtoPage.getContent());
        response.put("currentPage", proveedorDtoPage.getNumber() + 1);
        response.put("totalItems", proveedorDtoPage.getTotalElements());
        response.put("totalPages", proveedorDtoPage.getTotalPages());

        return ResponseEntity.ok(response);

    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ProveedorDto proveedorDto) {
        ProveedorDto proveedor = proveedorService.update(id, proveedorDto);
        if (proveedor != null) {
            return ResponseEntity.ok(proveedor);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);

    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (proveedorService.delete(id)) {
            return ResponseEntity.ok("Se ha eliminado al usuario con id " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido eliminar al usuario con id " + id);
    }
}
