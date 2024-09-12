package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import com.github.supercoding.web.dto.Spec;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-12T20:47:07+0900",
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
        if ( itemEntity.getId() != null ) {
            item.setId( String.valueOf( itemEntity.getId() ) );
        }
        item.setName( itemEntity.getName() );
        item.setType( itemEntity.getType() );
        item.setPrice( itemEntity.getPrice() );

        return item;
    }

    @Override
    public ItemEntity idAndItemBodyToItemEntity(Integer id, ItemBody itemBody) {
        if ( id == null && itemBody == null ) {
            return null;
        }

        ItemEntity.ItemEntityBuilder itemEntity = ItemEntity.builder();

        if ( itemBody != null ) {
            itemEntity.cpu( itemBodySpecCpu( itemBody ) );
            itemEntity.capacity( itemBodySpecCapacity( itemBody ) );
            itemEntity.name( itemBody.getName() );
            itemEntity.type( itemBody.getType() );
            itemEntity.price( itemBody.getPrice() );
        }
        itemEntity.id( id );
        itemEntity.stock( 0 );

        return itemEntity.build();
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

    private String itemBodySpecCpu(ItemBody itemBody) {
        if ( itemBody == null ) {
            return null;
        }
        Spec spec = itemBody.getSpec();
        if ( spec == null ) {
            return null;
        }
        String cpu = spec.getCpu();
        if ( cpu == null ) {
            return null;
        }
        return cpu;
    }

    private String itemBodySpecCapacity(ItemBody itemBody) {
        if ( itemBody == null ) {
            return null;
        }
        Spec spec = itemBody.getSpec();
        if ( spec == null ) {
            return null;
        }
        String capacity = spec.getCapacity();
        if ( capacity == null ) {
            return null;
        }
        return capacity;
    }
}
