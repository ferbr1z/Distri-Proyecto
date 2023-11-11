package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProveedorRepository extends JpaRepository<ProveedorBean, Integer> {
    Optional<ProveedorBean> findByIdAndActiveTrue(Integer id);

}
