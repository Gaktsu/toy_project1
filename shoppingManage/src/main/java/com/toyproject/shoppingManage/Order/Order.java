package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.Member.Member;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "member_id")
    private Long member_id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Long member_id, List<OrderItem> orderItems){
        this.member_id = member_id;

        for(var item : orderItems){
            this.orderItems.add(item);
            item.setOrder(this);
        }

        this.orderItems = orderItems;
    }
}
