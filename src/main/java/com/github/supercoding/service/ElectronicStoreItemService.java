package com.github.supercoding.service;

import com.github.supercoding.repository.items.ElectronicStoreItemRepository;
import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectronicStoreItemService {
    private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final StoreSalesRepository storeSalesRepository;


    public List<Item> findAllItem() {
        List<ItemEntity>itemEntities=electronicStoreItemRepository.findAllItems();
        return itemEntities.stream().map(Item::new).collect(Collectors.toList());
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity =new ItemEntity(0,itemBody.getName(),itemBody.getType()
        ,itemBody.getPrice(),itemBody.getSpec().getCpu(),itemBody.getSpec().getCapacity());
         return electronicStoreItemRepository.saveItem(itemEntity);
    }


    public Item findById(String id) {
        ItemEntity itemEntity = electronicStoreItemRepository.findItem(Integer.valueOf(id));
        return new Item(itemEntity);
    }

    public List<Item> findItemsByids(List<String> ids) {
        List<ItemEntity>itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream()
                .map(Item::new)
                .filter((item)->ids.contains(item.getId()))
                .collect(Collectors.toList());
    }

    public Integer deleteItem(String id) {
        return electronicStoreItemRepository.deleteItem(Integer.valueOf(id));
    }

    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(
                idInt,itemBody.getName(),itemBody.getType(),
                itemBody.getPrice(),itemBody.getSpec().getCpu(),
                itemBody.getSpec().getCapacity());
        ItemEntity itemEntityUpdated =electronicStoreItemRepository.updateItemEntity(idInt,itemEntity);

        return new Item(itemEntityUpdated);
    }

    @Transactional(transactionManager = "tm1")// 에러 날 시 롤백
    public Integer buyItems(BuyOrder buyOrder) {
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        ItemEntity itemEntity = electronicStoreItemRepository.findItem(itemId);

        if(itemEntity.getStoreId()==null) throw new RuntimeException("매장을 찾을 수 없습니다");
        if (itemEntity.getStock()<= 0)throw new RuntimeException("상품의 재고가 없습니다");

        Integer successBuyItemNums; //사려는 수량보다 적을 때 적은수량을 반환 아니면 그대로 줌
        if(itemNums > itemEntity.getStock()){successBuyItemNums = itemEntity.getStock()-itemEntity.getStock();}
        else successBuyItemNums = itemNums;

        Integer totalPrice = successBuyItemNums*itemEntity.getPrice();
        //item 재고 감소 db반영
        electronicStoreItemRepository.updateItemStock(itemId,itemEntity.getStock() - successBuyItemNums);

        //매장 매상 추가
        StoreSales storeSales = storeSalesRepository.findStoreSalesById(itemEntity.getStoreId());
        storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(),storeSales.getAmount()+totalPrice);

        return successBuyItemNums;
    }
}
