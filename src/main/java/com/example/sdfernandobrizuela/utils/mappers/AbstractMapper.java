package com.example.sdfernandobrizuela.utils.mappers;

import com.example.sdfernandobrizuela.abstracts.AbstractBean;
import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import com.example.sdfernandobrizuela.interfaces.IMapper;
import org.modelmapper.ModelMapper;

public abstract class AbstractMapper<T extends AbstractBean, K extends  AbstractDto> implements IMapper<T, K> {
    protected ModelMapper modelMapper = new ModelMapper();
}