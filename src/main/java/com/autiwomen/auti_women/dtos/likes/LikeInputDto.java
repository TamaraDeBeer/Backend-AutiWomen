package com.autiwomen.auti_women.dtos.likes;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeInputDto {

    @NotEmpty
    public Long id;

}
