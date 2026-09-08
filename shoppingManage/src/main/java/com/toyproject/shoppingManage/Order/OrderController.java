package com.toyproject.shoppingManage.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetOrder(@PathVariable @Min(value = 1) Long id){
        OrderResponseDTO responseBody = orderService.getOrder(id);

        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping
    public ResponseEntity<?> requestOrderProcess(@Valid @RequestBody OrderRequestDTO request){
        OrderResponseDTO responseBody = orderService.orderProcess(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> requestDeleteOrder(@PathVariable @Min(value = 1) Long id){
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}
