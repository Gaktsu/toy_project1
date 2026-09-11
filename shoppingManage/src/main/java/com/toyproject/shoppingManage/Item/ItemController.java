package com.toyproject.shoppingManage.Item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@Validated
public class ItemController {

    // ----------------------- FIELD --------------------------//

    private final ItemService itemService;

    // ----------------------- CONSTRUCTOR --------------------------//

    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    // ----------------------- RESTAPI : GET --------------------------//
    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetItem(@PathVariable @Min(value = 1) Long id){
        ItemResponseDTO responseBody = itemService.requestGetItem(id);

        return ResponseEntity.ok().body(responseBody);
    }

    // ----------------------- RESTAPI : POST --------------------------//

    @PostMapping
    public ResponseEntity<?> requestRegister(@Valid @RequestBody ItemRequestDTO request){
        ItemResponseDTO responseBody = itemService.requestRegisterItem(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
}
