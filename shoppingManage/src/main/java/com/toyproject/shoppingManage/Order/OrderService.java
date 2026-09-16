package com.toyproject.shoppingManage.Order;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import com.toyproject.shoppingManage.Item.Item;
import com.toyproject.shoppingManage.Item.ItemRepository;
import com.toyproject.shoppingManage.Item.ItemService;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import com.toyproject.shoppingManage.Member.Member;
import com.toyproject.shoppingManage.Member.MemberRepository;
import com.toyproject.shoppingManage.Member.MemberService;
import com.toyproject.shoppingManage.Order.Exception.NotEnoughStockException;
import com.toyproject.shoppingManage.Order.Exception.OrderNotFoundException;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    // ----------------------- FIELD --------------------------//

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // ----------------------- CONSTRUCTOR --------------------------//

    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository, ItemRepository itemRepository){
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    // ----------------------- RESTAPI : GET --------------------------//

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> requestGetOrders(Pageable pageable) {
        /* 일반적인 페이징
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.stream().map(OrderResponseDTO::from).toList();
        */

        // Collection Fetch Join + 페이징
        return orderRepository.findOrderWithItems(pageable).stream().map(OrderResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> requestGetOrdersByMemberId(Long id, Pageable pageable){
        return orderRepository.findOrdersByMemberId(id, pageable).stream().map(OrderResponseDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO requestGetOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponseDTO.from(order);
    }

    /*
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> test(Long id, @PageableDefault() Pageable pageable){
        // 1. 기본 메서드명으로 조건 검색해보기
        Order order = orderRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<OrderResponseDTO> orders = new ArrayList<>();
        orders.add(OrderResponseDTO.from(order));
        return orders;


        // 2. 메서드명으로 쿼리 시도하기
        // return orderRepository.findAllByMember_Id(id, pageable).stream().map(OrderResponseDTO::from).toList();
    }
    */

    // ----------------------- RESTAPI : POST --------------------------//

    @Transactional
    public OrderResponseDTO requestOrderProcess(OrderRequestDTO request){
        Member member = memberRepository.findById(request.memberId()).orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<OrderItem> items = new ArrayList<>();

        for(var requestItem : request.orderItems()){
            Item item = itemRepository.findById(requestItem.itemId()).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));

            item.decreaseStock(requestItem.quantity());
            itemRepository.save(item);

            items.add(OrderItem.from(requestItem, item));
        }

        Order order = new Order(member, items);

        orderRepository.save(order);

        return OrderResponseDTO.from(order);
    }

    // ----------------------- RESTAPI : DELETE --------------------------//

    @Transactional
    public void requestDeleteOrder(@Min(value = 1) Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(ErrorCode.ORDER_NOT_FOUND));

        for(var orderitem : order.getOrderItems()){
            Item item = orderitem.getItem();
            item.increaseStock(orderitem.getQuantity());
        }

        orderRepository.delete(order);
    }
}
