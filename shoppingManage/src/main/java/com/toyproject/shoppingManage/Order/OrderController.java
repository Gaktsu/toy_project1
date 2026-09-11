package com.toyproject.shoppingManage.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

    // ----------------------- FIELD --------------------------//

    private final OrderService orderService;

    // ----------------------- CONSTRUCTOR --------------------------//

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // ----------------------- RESTAPI : GET --------------------------//

    @GetMapping
    public ResponseEntity<?> requestGetOrders(){
        List<OrderResponseDTO> responseBody = orderService.requestGetOrders();

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetOrder(@PathVariable @Min(value = 1) Long id){
        OrderResponseDTO responseBody = orderService.requestGetOrder(id);

        return ResponseEntity.ok().body(responseBody);
    }

    // ----------------------- RESTAPI : POST --------------------------//

    @PostMapping
    public ResponseEntity<?> requestOrderProcess(@Valid @RequestBody OrderRequestDTO request){
        OrderResponseDTO responseBody = orderService.requestOrderProcess(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    // ----------------------- RESTAPI : DELETE --------------------------//

    @DeleteMapping("/{id}")
    public ResponseEntity<?> requestDeleteOrder(@PathVariable @Min(value = 1) Long id){
        orderService.requestDeleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}
