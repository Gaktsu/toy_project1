package com.toyproject.shoppingManage.Order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> requestOrderProcess(@Valid @RequestBody OrderRequestDTO request){
        OrderResponseDTO responseBody = orderService.orderProcess(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

}
