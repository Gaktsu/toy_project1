package com.toyproject.shoppingManage.Order.OrderItems;

import com.toyproject.shoppingManage.Item.Item;

public record OrderItemResponseDTO(
        Long itemId,
        String itemName,
        int itemPrice,
        int quantity
) {
    public static OrderItemResponseDTO from(Item item, OrderItem orderItem){
        return new OrderItemResponseDTO(item.getId(), item.getName(), item.getPrice(), orderItem.getQuantity());
    }
}
