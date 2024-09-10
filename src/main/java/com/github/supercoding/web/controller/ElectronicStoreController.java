package com.github.supercoding.web.controller;

import com.github.supercoding.repository.storeSales.StoreSalesRepository;
import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.BuyOrder;
import com.github.supercoding.web.dto.Item;
import com.github.supercoding.web.dto.ItemBody;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ElectronicStoreController {

    private ElectronicStoreItemService electronicStoreItemService;

    public ElectronicStoreController(ElectronicStoreItemService electronicStoreItemService) {
        this.electronicStoreItemService = electronicStoreItemService;
    }

//    private static int serialItemID =1;
//    private List<Item> items = new ArrayList<>(Arrays.asList(
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","대충해"),
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","대충"),
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","해")
//    ));

    @GetMapping("/items")
    public List<Item> findAllItem(){
        return electronicStoreItemService.findAllItem();
    };

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
//        Item newItem= new Item(serialItemID++,itemBody);
//        items.add(newItem);
//        return "ID: "+newItem.getId();

        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: " +itemId;
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id){
//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        return itemFounded;
//        List<ItemEntity>itemEntities=electronicStoreItemRepository.findAllItems();
//        return itemEntities.stream().map(Item::new).collect(Collectors.toList());
        return electronicStoreItemService.findById(id);
    }

    @GetMapping("/items-query")
    public Item findItemByQueryId(@RequestParam("id") String id){
//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        return itemFounded;
        return electronicStoreItemService.findById(id);
    }
    @GetMapping("/items-queries") //복수의 데이터를 받는다
    public List<Item> findItemByQueryIds(@RequestParam("id") List<String> ids){

//        Set<String> idSet = ids.stream().collect(Collectors.toSet());
//
//        List<Item> itemsFound  = items.stream()
//                .filter(item->idSet.contains(item.getId()))
//                .collect(Collectors.toList());
//
//
        return electronicStoreItemService.findItemsByids(ids);
    }
    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id){

//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        items.remove(itemFounded);

//        return "Object with id"+ itemFounded.getId()+"has been deleted";

        Integer idInt = electronicStoreItemService.deleteItem(id);
        return "ID: " +idInt +"has been deleted";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id,@RequestBody ItemBody itemBody){
//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        items.remove(itemFounded);
//        //원래있던것을 없애고 새로만듦
//        Item itemUpdated = new Item(Integer.valueOf(id),itemBody);
//        items.add(itemUpdated);
        return electronicStoreItemService.updateItem(id,itemBody);
    }
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer OrderItemNums= electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 "+OrderItemNums+"개를 구매 하였습니다.";
    }
}
