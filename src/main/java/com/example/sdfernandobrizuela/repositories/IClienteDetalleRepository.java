package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteDetalleRepository extends JpaRepository<ClienteDetalleBean,Integer> {
    ClienteDetalleBean findByClienteId(Integer id);
}
