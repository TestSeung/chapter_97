package com.github.supercoding.repository.items;

import java.util.List;

public interface ElectronicStoreItemRepository {

    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    ItemEntity findItem(Integer idInt);

    Integer deleteItem(Integer idInt);

    void updateItemStock(Integer itemId, Integer stock);
}
