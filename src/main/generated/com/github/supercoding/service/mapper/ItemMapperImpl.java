package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.Spec;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-11T19:58:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item itemEntityToItem(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        Item item = new Item();

        item.setSpec( itemEntityToSpec( itemEntity ) );
        item.setId( String.valueOf( itemEntity.getId() ) );
        item.setName( itemEntity.getName() );
        item.setType( itemEntity.getType() );
        item.setPrice( itemEntity.getPrice() );

        return item;
    }

    protected Spec itemEntityToSpec(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        Spec spec = new Spec();

        spec.setCpu( itemEntity.getCpu() );
        spec.setCapacity( itemEntity.getCapacity() );

        return spec;
    }
}
