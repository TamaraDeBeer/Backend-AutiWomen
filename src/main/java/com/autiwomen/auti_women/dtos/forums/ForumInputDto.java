package com.autiwomen.auti_women.dtos.forums;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForumInputDto {

    public String name;

    @NotEmpty
    @Size(min = 1, max = 50)
    public String title;

    @NotEmpty
    @Size(min = 1, max = 4000)
    public String text;

    public String age;
    public String date;
    public String lastReaction;

    @NotEmpty
    public String topic;

}
