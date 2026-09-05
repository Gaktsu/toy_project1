package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.Member.Member;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItemResponseDTO;

import java.util.ArrayList;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Member member,
        List<OrderItemResponseDTO> items
) {

    public static OrderResponseDTO from(Order order){
        List<OrderItemResponseDTO> responseItems = new ArrayList<>();

        for(var orderItem : order.getOrderItems()){
            responseItems.add(OrderItemResponseDTO.from(orderItem.getItem(), orderItem));
        }

        return new OrderResponseDTO(order.getId(), order.getMember(), responseItems);
    }
}
