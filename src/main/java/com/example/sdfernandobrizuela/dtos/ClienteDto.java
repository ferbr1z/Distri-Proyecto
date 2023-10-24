package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;

public class ClienteDto extends AbstractDto {
    private Integer id;
    private String nombre;
    private String cedula;
    private String telefono;
    private String email;

    public ClienteDto(Integer id, String nombre, String cedula, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
