package com.github.supercoding.web.dto;

import com.github.supercoding.repository.items.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Getter
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String type;
    private Integer price;
    private Spec spec;


    public Item(Integer id, ItemBody itemBody) {
        this.id = String.valueOf(id);
        this.name = itemBody.getName();
        this.type = itemBody.getType();
        this.price = itemBody.getPrice();
        this.spec = itemBody.getSpec();
    }

    public Item(String id, String name, String type, Integer price, String cpu,String capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.spec = new Spec(cpu,capacity);
    }
    public Item(ItemEntity itemEntity) {
        this.id = String.valueOf(itemEntity.getId());
        this.name = itemEntity.getName();
        this.type = itemEntity.getType();
        this.price = itemEntity.getPrice();
        this.spec = new Spec(itemEntity.getCpu(),itemEntity.getCapacity());
    }

}
