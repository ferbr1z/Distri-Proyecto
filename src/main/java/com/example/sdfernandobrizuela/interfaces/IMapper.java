package com.example.sdfernandobrizuela.interfaces;

public interface IMapper<Bean extends IBean, Dto extends IDto> {

    public Bean toBean(Dto dto);
    public Dto toDto(Bean bean);

}
