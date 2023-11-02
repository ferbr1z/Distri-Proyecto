package com.example.sdfernandobrizuela.abstracts;

import com.example.sdfernandobrizuela.interfaces.IBean;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractBean implements IBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "boolean default true")
    private boolean isActive;
    @Override
    public Integer getId() {
        return id;
    }
}
