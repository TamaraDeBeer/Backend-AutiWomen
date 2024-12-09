package com.autiwomen.auti_women.dtos.comments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInputDto {

    public String name;

    @NotEmpty
    @Size(min = 1, max = 2000)
    public String text;

    public LocalDate date;
    public String age;

}
