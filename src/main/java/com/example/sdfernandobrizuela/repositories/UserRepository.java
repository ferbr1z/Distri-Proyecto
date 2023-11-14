package com.example.sdfernandobrizuela.repositories;

import com.example.sdfernandobrizuela.beans.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserBean, Integer> {
    Optional<UserBean> findByUsername(String username);
    Optional<UserBean> findByEmail(String email);
}
