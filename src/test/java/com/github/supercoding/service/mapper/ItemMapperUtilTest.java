package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.web.dto.Items.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class ItemMapperUtilTest {

    @DisplayName("ItemEntity의 itemEntityToItem 메소드 테스트")
    @Test
    void itemEntityToItem() {
        //given
        ItemEntity itemEntity = new ItemEntity().builder()
                .name("name")
                .type("type")
                .id(1)
                .price(1000)
                .stock(0)
                .cpu("CPU1")
                .capacity("5G")
                .storeSales(new StoreSales())
                .build();
        //when
        Item item =ItemMapper.ISTANCE.itemEntityToItem(itemEntity);
        //then
        log.info("만들어진 item"+item);
        assertEquals(itemEntity.getPrice(), item.getPrice());
        assertEquals(itemEntity.getName(), item.getName());
        assertEquals(itemEntity.getCapacity(), item.getSpec().getCapacity());
        assertEquals(itemEntity.getCpu(), item.getSpec().getCpu());

    }
}