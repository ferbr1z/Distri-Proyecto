package com.example.sdfernandobrizuela.abstracts;

import com.example.sdfernandobrizuela.interfaces.IBean;

public abstract class AbstractDto implements IBean {
    private Integer id;

    @Override
    public Integer getId(){
        return id;
    }
}
