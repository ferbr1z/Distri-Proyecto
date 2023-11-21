package com.example.sdfernandobrizuela.controllers;

import com.example.sdfernandobrizuela.dtos.cliente.ClienteDetalleDto;
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
@RequestMapping("/clientes-detalles")
public class ClienteDetalleController implements IController<ClienteDetalleDto> {

    @Autowired
    private IService<ClienteDetalleDto> clienteDetalleService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity create(@RequestBody ClienteDetalleDto clienteDetalleDto) {
        return ResponseEntity.ok(clienteDetalleService.create(clienteDetalleDto));
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity getById(@PathVariable Integer id) {
        ClienteDetalleDto detalleDto = clienteDetalleService.getById(id);
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
        Page ClienteDetalleDto = clienteDetalleService.getAll(pag);

        Map<String, Object> response = new HashMap<>();
        response.put("clientes-detalles", ClienteDetalleDto.getContent());
        response.put("currentPage", ClienteDetalleDto.getNumber() + 1);
        response.put("totalItems", ClienteDetalleDto.getTotalElements());
        response.put("totalPages", ClienteDetalleDto.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody ClienteDetalleDto clienteDetalleWithClienteDto) {
        ClienteDetalleDto detalleDto = clienteDetalleService.update(id, clienteDetalleWithClienteDto);
        if (detalleDto != null) {
            return ResponseEntity.ok(detalleDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el cliente detalle con id " + id);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity delete(@PathVariable Integer id) {
        if (clienteDetalleService.delete(id)) {
            return ResponseEntity.ok("Se ha borrado el detalle de cliente " + id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha podido borrar el detalle de cliente " + id);
    }
}
