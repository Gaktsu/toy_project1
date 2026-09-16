package com.toyproject.shoppingManage.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<?> requestGetOrders(@PageableDefault(page = 0, size = 10) Pageable pageable){
        List<OrderResponseDTO> responseBody = orderService.requestGetOrders(pageable);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> requestGetOrder(@PathVariable @Min(value = 1) Long id){
        OrderResponseDTO responseBody = orderService.requestGetOrder(id);

        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/member")
    public ResponseEntity<?> requestGetOrderByMemberId(
            @RequestParam("memberId")
            @PathVariable
            @Min(value = 1)
            Long memberId,

            @PageableDefault(page = 0, size = 10)
            Pageable pageable){
        List<OrderResponseDTO> responseBody = orderService.requestGetOrdersByMemberId(memberId, pageable);

        return ResponseEntity.ok().body(responseBody);
    }

    /*
    @GetMapping("test")
    public ResponseEntity<?> requestTest(
            @RequestParam("memberId")
            @PathVariable
            @Min(value = 1)
            Long memberId,

            @PageableDefault()
            Pageable pageable){
        List<OrderResponseDTO> responseBody = orderService.test(memberId, pageable);

        return ResponseEntity.ok().body(responseBody);
    }
    */

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
