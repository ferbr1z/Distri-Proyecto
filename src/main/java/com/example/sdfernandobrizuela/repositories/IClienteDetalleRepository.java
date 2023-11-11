package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ClienteDetalleBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteDetalleRepository extends JpaRepository<ClienteDetalleBean,Integer> {
    Optional<ClienteDetalleBean> findByClienteId(Integer id);

}
