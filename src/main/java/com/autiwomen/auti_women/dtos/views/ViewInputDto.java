package com.autiwomen.auti_women.dtos.views;

public class ViewInputDto {

    public Long id;

    public ViewInputDto(Long id) {
        this.id = id;
    }

    public ViewInputDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
