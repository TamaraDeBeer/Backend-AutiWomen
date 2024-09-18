package com.autiwomen.auti_women.dtos.likes;

public class LikeInputDto {

    public Long id;
    public Long user_id;
    public Long forum_id;

    public LikeInputDto(Long id, Long user_id, Long forum_id) {
        this.id = id;
        this.user_id = user_id;
        this.forum_id = forum_id;
    }

    public LikeInputDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getForum_id() {
        return forum_id;
    }

    public void setForum_id(Long forum_id) {
        this.forum_id = forum_id;
    }
}
