package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteBean, Integer> {
    Optional<ClienteBean> findByIdAndActiveTrue(Integer id);
    Boolean existsByIdAndActiveTrue(Integer id);
    Page<ClienteBean> findAllByActiveTrue(Pageable pageable);
}
