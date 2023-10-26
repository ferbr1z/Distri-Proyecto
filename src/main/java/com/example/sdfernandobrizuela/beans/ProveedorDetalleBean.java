package com.example.sdfernandobrizuela.beans;

import com.example.sdfernandobrizuela.abstracts.AbstractBean;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedor_detalle")
public class ProveedorDetalleBean extends AbstractBean {
    @Column
    private String direccion;
    @Column
    private String telefono;
    @Column
    private String email;
    @OneToOne
    @JoinColumn(name = "proveedor_id")
    private ProveedorBean proveedor;
}
