package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ProveedorDetalleBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProveedorDetalleRepository extends JpaRepository<ProveedorDetalleBean, Integer> {
    Optional<ProveedorDetalleBean> findByIdAndActiveTrue(Integer id);
}
