package com.example.sdfernandobrizuela.beans;

import com.example.sdfernandobrizuela.abstracts.AbstractBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class ClienteBean extends AbstractBean {

    @Column
    private String nombre;
    @Column(unique = true)
    private String ruc;
    @Column(unique = true)
    private String cedula;
}