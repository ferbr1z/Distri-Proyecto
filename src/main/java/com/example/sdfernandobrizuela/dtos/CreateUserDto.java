package com.example.sdfernandobrizuela.dtos;

import com.example.sdfernandobrizuela.abstracts.AbstractDto;
import lombok.Data;

@Data
public class CreateUserDto extends AbstractDto {
    private String email;
    private String username;
    private String password;
}
