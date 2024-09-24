package com.autiwomen.auti_women.dtos.likes;

public class LikeInputDto {

    public Long id;

    public LikeInputDto(Long id) {
        this.id = id;

    }

    public LikeInputDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
