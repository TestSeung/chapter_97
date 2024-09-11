package com.github.supercoding.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ItemBody {
    private String name;
    private String type;
    private Integer price;
    private Spec spec;

}
