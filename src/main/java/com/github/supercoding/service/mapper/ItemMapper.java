package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.Items.Item;
import com.github.supercoding.web.dto.Items.ItemBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    //singleton
    ItemMapper ISTANCE = Mappers.getMapper(ItemMapper.class);
    //Method
    @Mapping(target="spec.cpu",source = "cpu")
    @Mapping(target = "spec.capacity",source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);

    @Mapping(target = "cpu",source = "itemBody.spec.cpu")
    @Mapping(target = "capacity", source = "itemBody.spec.capacity")
    @Mapping(target= "storeSales",ignore = true)
    @Mapping(target = "stock",expression = "java(0)")
    ItemEntity idAndItemBodyToItemEntity(Integer id, ItemBody itemBody);
}
