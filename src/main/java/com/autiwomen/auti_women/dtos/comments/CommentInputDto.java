package com.autiwomen.auti_women.dtos.comments;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInputDto {

    public String name;

    @Size(min = 1, max = 2000)
    public String text;

    public String date;
    public String age;

}
