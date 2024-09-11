package com.github.supercoding.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Spec {
    @Schema(name ="cpu",description = "Item CPU",example = "google tensor")private String cpu;
    @Schema(name ="capacity",description = "Item 용량 spec",example = "25G")private String capacity;

}
