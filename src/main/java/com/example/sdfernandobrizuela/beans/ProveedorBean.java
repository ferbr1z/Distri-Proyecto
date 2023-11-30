package com.example.sdfernandobrizuela.beans;

import com.example.sdfernandobrizuela.abstracts.AbstractBean;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="proveedores")
public class ProveedorBean extends AbstractBean {
    @Column
    private String nombre;
    @Column(unique = true)
    private String ruc;
}
