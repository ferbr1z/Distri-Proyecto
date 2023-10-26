package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorDetalleRepository extends JpaRepository<ProveedorDetalleBean, Integer> {
    ProveedorDetalleBean findByProveedorId(Integer id);
}
