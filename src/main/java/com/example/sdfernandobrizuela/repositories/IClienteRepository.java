package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.ClienteBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteBean,Integer> {
}
