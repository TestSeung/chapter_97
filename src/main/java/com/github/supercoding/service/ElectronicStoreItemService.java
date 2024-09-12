package com.github.supercoding.service;

import com.github.supercoding.repository.items.ElectronicStoreItemJpaRepository;
import com.github.supercoding.repository.items.ElectronicStoreItemRepository;
import com.github.supercoding.repository.items.ItemEntity;
import com.github.supercoding.repository.storeSales.StoreSales;
import com.github.supercoding.repository.storeSales.StoreSalesJpaRepository;
import com.github.supercoding.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.service.mapper.ItemMapper;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElectronicStoreItemService {

   // private final Logger logger= LoggerFactory.getLogger(this.getClass()); //Slf4j
    private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final ElectronicStoreItemJpaRepository electronicStoreItemJpaRepository;
    private final StoreSalesRepository storeSalesRepository;
    private final StoreSalesJpaRepository storeSalesJpaRepository;


    public List<Item> findAllItem() {
        List<ItemEntity>itemEntities=electronicStoreItemJpaRepository.findAll();
        return itemEntities.stream().map(ItemMapper.ISTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity =new ItemEntity(0,itemBody.getName(),itemBody.getType()
        ,itemBody.getPrice(),itemBody.getSpec().getCpu(),itemBody.getSpec().getCapacity());

        ItemEntity itemEntityCreated=electronicStoreItemJpaRepository.save(itemEntity);

      //  return electronicStoreItemRepository.saveItem(itemEntity);
        return itemEntityCreated.getId();
    }


    public Item findById(String id) {
       // ItemEntity itemEntity = electronicStoreItemRepository.findItem(Integer.valueOf(id));
        Integer idInt=Integer.parseInt(id);

        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(idInt)
                .orElseThrow(()->new NotFoundException("해당 id를 찾지 못하였습니다."));

        Item item =ItemMapper.ISTANCE.itemEntityToItem(itemEntity);
        return item;
    }

    public List<Item> findItemsByids(List<String> ids) {
        List<ItemEntity>itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream()
                .map(ItemMapper.ISTANCE::itemEntityToItem)
                .filter((item)->ids.contains(item.getId()))
                .collect(Collectors.toList());
    }

    public void deleteItem(String id) {
        //return electronicStoreItemRepository.deleteItem(Integer.valueOf(id));
        Integer itemId = Integer.parseInt(id);
        electronicStoreItemJpaRepository.deleteById(itemId);
    }

    @Transactional(transactionManager = "tmJpa1")
    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);


//        ItemEntity itemEntity = new ItemEntity(
//                idInt,itemBody.getName(),itemBody.getType(),
//                itemBody.getPrice(),itemBody.getSpec().getCpu(),
//                itemBody.getSpec().getCapacity());

        ItemEntity itemEntityUpdated =electronicStoreItemJpaRepository.findById(idInt)
                .orElseThrow(()-> new NotFoundException("해당 ID: "+idInt+ "찾을 수 없다."));

        itemEntityUpdated.setItemBody(itemBody);

        return ItemMapper.ISTANCE.itemEntityToItem(itemEntityUpdated);
    }

    @Transactional(transactionManager = "tmJpa1")// 에러 날 시 롤백
    public Integer buyItems(BuyOrder buyOrder) {
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        ItemEntity itemEntity = electronicStoreItemJpaRepository.findById(itemId)
                .orElseThrow(()->new NotFoundException("해당 이름을 찾을 수 없습니다."));

        if(itemEntity.getStoreId()==null) {
            log.error("매장을 찾을 수 없습니다.");
            throw new RuntimeException("매장을 찾을 수 없습니다");
        }

        if (itemEntity.getStock()<= 0)throw new RuntimeException("상품의 재고가 없습니다");

        Integer successBuyItemNums; //사려는 수량보다 적을 때 적은수량을 반환 아니면 그대로 줌
        if(itemNums > itemEntity.getStock()){successBuyItemNums = itemEntity.getStock()-itemEntity.getStock();}
        else successBuyItemNums = itemNums;

        Integer totalPrice = successBuyItemNums*itemEntity.getPrice();
        //item 재고 감소 db반영
        //electronicStoreItemRepository.updateItemStock(itemId,itemEntity.getStock() - successBuyItemNums);
        //영속성 포함
        itemEntity.setStock(itemEntity.getStock() - successBuyItemNums);

        //매장 매상 추가
        StoreSales storeSales = storeSalesJpaRepository.findById(itemEntity.getStoreId()).orElseThrow(()->new NotFoundException("요청하신 StoreId ㄴㄴ"));
       // storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(),storeSales.getAmount()+totalPrice);
        storeSales.setAmount(storeSales.getAmount()-totalPrice);


        return successBuyItemNums;
    }

    public List<Item> findItemsByTypes(List<String> types) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findItemEntitiesByTypeIn(types); //sql문
        return itemEntities.stream().map(ItemMapper.ISTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public List<Item> findItemsOrderByPrices(Integer maxValue) {
        List<ItemEntity> itemEntities = electronicStoreItemJpaRepository.findItemEntitiesByPriceLessThanEqualOrderByPriceAsc(maxValue);
        return itemEntities.stream().map(ItemMapper.ISTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Page<Item> findAllWithPageable(Pageable pageable) {
        Page<ItemEntity> itemEntities= electronicStoreItemJpaRepository.findAll(pageable);
        return itemEntities.map(ItemMapper.ISTANCE::itemEntityToItem);
    }
    public Page<Item> findAllWithPageable(List<String>types,Pageable pageable) {
        Page<ItemEntity> itemEntities= electronicStoreItemJpaRepository.findAllByTypeIn(types,pageable);
        return itemEntities.map(ItemMapper.ISTANCE::itemEntityToItem);
    }
}
