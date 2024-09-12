package com.github.supercoding.web.controller;

import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.Items.BuyOrder;
import com.github.supercoding.web.dto.Items.Item;
import com.github.supercoding.web.dto.Items.ItemBody;
import com.github.supercoding.web.dto.Items.StoreInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor //의존성주입
@Slf4j
public class ElectronicStoreController {


    //bean은 final로
    private final ElectronicStoreItemService electronicStoreItemService;

    //    private static int serialItemID =1;
//    private List<Item> items = new ArrayList<>(Arrays.asList(
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","대충해"),
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","대충"),
//            new Item(String.valueOf(serialItemID++),"Apple iPhone 12 Pro Max","SmartPhone",123456,"몰라","해")
//    ));
    @Operation(summary = "모든 Item을 검색")
    @GetMapping("/items")
    public List<Item> findAllItem(){
        log.info("GET /Items 요청 들어옴.");
        //return electronicStoreItemService.findAllItem();
        List<Item>items =electronicStoreItemService.findAllItem();
        log.info("GET /items 응답: "+items);
        return items;
    };
    @Operation(summary = "단일 Item을 등록")
    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
//        Item newItem= new Item(serialItemID++,itemBody);
//        items.add(newItem);
//        return "ID: "+newItem.getId();

        Integer itemId = electronicStoreItemService.saveItem(itemBody);
        return "ID: " +itemId;
    }
    @Operation(summary = "단일 Item id로 검색")
    @GetMapping("/items/{id}")
    public Item findItemByPathId(
            @Parameter(name ="id",description ="item ID",in = ParameterIn.PATH,example = "1")
            @PathVariable String id){
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
    @Operation(summary = "단일 Item id 쿼리문을 검색")
    @GetMapping("/items-query")
    public Item findItemByQueryId(
            @Parameter(name ="id",description ="item ID",in = ParameterIn.PATH,example = "1")
            @RequestParam("id") String id){
//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        return itemFounded;

        return electronicStoreItemService.findById(id);
    }
    @Operation(summary = "id로 복수의 Item을 검색")
    @GetMapping("/items-queries") //복수의 데이터를 받는다
    public List<Item> findItemByQueryIds(
            @Parameter(name ="ids",description ="item IDs",in = ParameterIn.PATH,example = "[1,2,3]")
            @RequestParam("id") List<String> ids){

//        Set<String> idSet = ids.stream().collect(Collectors.toSet());
//
//        List<Item> itemsFound  = items.stream()
//                .filter(item->idSet.contains(item.getId()))
//                .collect(Collectors.toList());
//
//
        return electronicStoreItemService.findItemsByids(ids);
    }
    @Operation(summary = "단일 Item을 삭제")
    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id){

//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        items.remove(itemFounded);

//        return "Object with id"+ itemFounded.getId()+"has been deleted";

        electronicStoreItemService.deleteItem(id);
        return "ID: "+id+" has been deleted";
    }
    @Operation(summary = "단일 Item을 수정")
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
    @Operation(summary = "Item을 구매")
    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder){
        Integer OrderItemNums= electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 "+OrderItemNums+"개를 구매 하였습니다.";
    }
    @Operation(summary = "id로 복수의 Item을 검색")
    @GetMapping("/items-types") //복수의 데이터를 받는다
    public List<Item> findItemByTypes(
            @RequestParam("type") List<String> types){
        return electronicStoreItemService.findItemsByTypes(types);
    }

    @Operation(summary = "단일 Item id 쿼리문을 검색")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(
            @RequestParam("max") Integer maxValue){
        return electronicStoreItemService.findItemsOrderByPrices(maxValue);
    }

    @Operation(summary = "pagination 지원")
    @GetMapping("/items-page")
    public Page<Item> findItemsPagination(Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(pageable);
    }

    @Operation(summary = "pagination 지원2")
    @GetMapping("/items-types-page")
    public Page<Item> findItemsPagination(@RequestParam("type")List<String> types, Pageable pageable){
        return electronicStoreItemService.findAllWithPageable(types,pageable);
    }

    @GetMapping("/stores")
    public List<StoreInfo> findAllStoreInfo(){
        return electronicStoreItemService.findAllStoreInfo();
    }
}
