package com.example.sdfernandobrizuela.interfaces;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IController <T extends AbstractDto>{
    public ResponseEntity create(T t);
    public ResponseEntity getById(Integer id);
    public ResponseEntity<Map<String, Object>> getAll(@PathVariable(value = "page_num") Integer page);
    public ResponseEntity update(Integer id, T t);
    public ResponseEntity delete(Integer id);
}