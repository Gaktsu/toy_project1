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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> orderItems = new ArrayList<>();

    public Order(Member member, List<OrderItem> orderItems){
        this.member = member;

        for(var item : orderItems){
            this.orderItems.add(item);
            item.setOrder(this);
        }
    }
}
