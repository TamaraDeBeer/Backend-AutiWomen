package com.autiwomen.auti_women.dtos.views;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewInputDto {

    @NotEmpty
    public Long id;

}
