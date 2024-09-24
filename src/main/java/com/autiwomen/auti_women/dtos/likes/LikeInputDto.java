package com.autiwomen.auti_women.dtos.likes;

public class LikeInputDto {

    public Long id;
    public String username;
    public Long forum_id;

    public LikeInputDto(Long id, String username, Long forum_id) {
        this.id = id;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getForum_id() {
        return forum_id;
    }

    public void setForum_id(Long forum_id) {
        this.forum_id = forum_id;
    }
}
