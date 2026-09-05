package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import com.toyproject.shoppingManage.Item.Item;
import com.toyproject.shoppingManage.Item.ItemRepository;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import com.toyproject.shoppingManage.Member.Member;
import com.toyproject.shoppingManage.Member.MemberRepository;
import com.toyproject.shoppingManage.Order.Exception.NotEnoughStockException;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import org.springframework.stereotype.Service;

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

            int left = item.getStock() - requestItem.quantity();
            item.setStock(left);
            itemRepository.save(item);

            items.add(OrderItem.from(requestItem, item));
        }

        Order order = new Order(member.getId(), items);

        orderRepository.save(order);

        return OrderResponseDTO.from(order);
    }
}
