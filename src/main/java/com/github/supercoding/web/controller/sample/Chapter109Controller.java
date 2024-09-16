package com.github.supercoding.web.controller.sample;

import com.github.supercoding.repository.items.ElectronicStoreItemJpaRepository;
import com.github.supercoding.service.ElectronicStoreItemService;
import com.github.supercoding.web.dto.Items.Item;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/sample")
@RequiredArgsConstructor
@Slf4j
public class Chapter109Controller {
    private final ElectronicStoreItemService electronicStoreItemService;

    @Operation(summary = "단일 Item id 쿼리문을 검색")
    @GetMapping("/items-prices")
    public List<Item> findItemByPrices(
            HttpServletRequest httpServletRequest
//            @RequestParam("max") Integer maxValue
  ){
        Integer maxPrice = Integer.valueOf(httpServletRequest.getParameter("max"));
        List<Item> items =  electronicStoreItemService.findItemsOrderByPrices(maxPrice);
        return items;
    }

    @Operation(summary = "단일 Item을 삭제")
    @DeleteMapping("/items/{id}")
    public void deleteItemByPathId(@PathVariable String id,
                                   HttpServletResponse httpServletResponse) throws IOException {

//        Item itemFounded = items.stream()
//                .filter(item->item.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RuntimeException());
//
//        items.remove(itemFounded);

//        return "Object with id"+ itemFounded.getId()+"has been deleted";

        electronicStoreItemService.deleteItem(id);
        String responseMessage = id+"has been deleted";
        httpServletResponse.getOutputStream().print(responseMessage);
    }
}
