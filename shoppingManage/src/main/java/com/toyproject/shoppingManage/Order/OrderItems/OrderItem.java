package com.toyproject.shoppingManage.Order.OrderItems;

import com.toyproject.shoppingManage.Item.Item;
import com.toyproject.shoppingManage.Order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Order_Items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderItem {

    // ----------------------- FIELD --------------------------//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    // ----------------------- CONSTRUCTOR --------------------------//

    public OrderItem(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    // ----------------------- FACTORY METHOD --------------------------//

    public static OrderItem from(OrderItemRequestDTO request, Item item){
        return new OrderItem(item, request.quantity());
    }

    // ----------------------- METHOD --------------------------//

    public void setOrder(Order order){ this.order = order; }
}
