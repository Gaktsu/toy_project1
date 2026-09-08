package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import com.toyproject.shoppingManage.Item.Item;
import com.toyproject.shoppingManage.Item.ItemRepository;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import com.toyproject.shoppingManage.Member.Member;
import com.toyproject.shoppingManage.Member.MemberRepository;
import com.toyproject.shoppingManage.Order.Exception.NotEnoughStockException;
import com.toyproject.shoppingManage.Order.Exception.OrderNotFoundException;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository){
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    public OrderResponseDTO orderProcess(OrderRequestDTO request){
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<OrderItem> items = new ArrayList<>();

        for(var requestItem : request.orderItems()){
            Item item = itemRepository.findById(requestItem.itemId()).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));

            if(item.getStock() < requestItem.quantity())
                throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK);

            item.decreaseStock(requestItem.quantity());
            itemRepository.save(item);

            items.add(OrderItem.from(requestItem, item));
        }

        Order order = new Order(member, items);

        orderRepository.save(order);

        return OrderResponseDTO.from(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponseDTO.from(order);
    }

    @Transactional
    public void deleteOrder(@Min(value = 1) Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));

        for(var orderitem : order.getOrderItems()){
            Item item = orderitem.getItem();
            item.increaseStock(orderitem.getQuantity());
        }

        orderRepository.delete(order);
    }
}
