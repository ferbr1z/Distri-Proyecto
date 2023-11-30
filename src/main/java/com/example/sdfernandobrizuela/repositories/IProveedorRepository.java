package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProveedorRepository extends JpaRepository<ProveedorBean, Integer> {
    Optional<ProveedorBean> findByIdAndActiveTrue(Integer id);
    Page<ProveedorBean> findAllByActiveTrue(Pageable pageable);
    Page<ProveedorBean> findByNombreIgnoreCaseContaining(String nombre, Pageable pageable);
    Page<ProveedorBean> findByRucContaining(String nombre, Pageable pag);
}