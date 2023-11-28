package com.example.sdfernandobrizuela.abstracts;

import com.example.sdfernandobrizuela.interfaces.IBean;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@MappedSuperclass
public abstract class AbstractBean implements IBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "boolean")
    @ColumnDefault("true")
    private boolean active;

    @Override
    public Integer getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
