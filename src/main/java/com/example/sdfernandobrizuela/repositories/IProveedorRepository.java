package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ProveedorBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedorRepository extends JpaRepository<ProveedorBean, Integer> {
}
