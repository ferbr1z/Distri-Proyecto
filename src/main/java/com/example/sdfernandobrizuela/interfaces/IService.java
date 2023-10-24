package com.example.sdfernandobrizuela.interfaces;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IService<T extends AbstractDto> {
    public T getById(Integer id);
    public List<T> getAll(Pageable pag);
    public T update(Integer id, T t);
    public boolean delete(Integer id);
}
