package com.toyproject.shoppingManage.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 너무 깊게 들어갔다고 판단
    /*
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from order_items where order_id = :order_id", nativeQuery = true)
    void deleteOrderItemsByOrderId(@Param("order_id") Long id);

    @Query("select o from Order o " +
            "join fetch o.orderItems oi " +  // 💡 o(Order)가 가진 자식 필드명(orderItems)을 적습니다.
            "join fetch oi.item i " +        // 💡 oi(OrderItem)가 가진 상품 필드명(item)을 적습니다.
            "where o.id = :order_id")
    Optional<Order> findOrderWithItems(@Param("order_id") Long id);
    */
}
