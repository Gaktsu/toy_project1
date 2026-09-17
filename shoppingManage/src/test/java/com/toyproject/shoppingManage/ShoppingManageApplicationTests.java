package com.toyproject.shoppingManage;

import com.toyproject.shoppingManage.Item.*;
import com.toyproject.shoppingManage.Member.*;
import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import com.toyproject.shoppingManage.Order.*;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItemRequestDTO;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItemResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ShoppingManageApplicationTests {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderService orderService;

	@Test
	@DisplayName("회원 정보를 성공적으로 저장한다")
	void MemberRegisterTest() {
		// given
		// 테스트용 이름, 이메일 준비
		MemberRequestDTO request = new MemberRequestDTO("홍길동", "Hong@naver.com");

		// when
		// 회원 생성
		MemberResponseDTO response = memberService.requestRegisterMember(request);


		// then
		// member 중복 검사
		assertThatThrownBy(() -> memberService.requestRegisterMember(request)).isInstanceOf(MemberNotFoundException.class);
	}

	@Test
	@DisplayName("주문 생성 시 재고 감소한다")
	void DecreseStockAfterOrder(){
		// given
		// 회원, 아이템, 주문 개
;
		String memberName = "홍길동";
		String memberEmail = "Hong@gmail.com";

		MemberRequestDTO memberRequest = new MemberRequestDTO(memberName, memberEmail);
		MemberResponseDTO memberResponse = memberService.requestRegisterMember(memberRequest);

		String itemName = "싱싱한 딸기";
		Integer itemPrice = 5000;
		Integer itemStock = 40;

		ItemRequestDTO itemRequest = new ItemRequestDTO(itemName, itemPrice, itemStock);
		ItemResponseDTO itemResponse = itemService.requestRegisterItem(itemRequest);

		Integer orderItemQuantity = 10;

		List<OrderItemRequestDTO> orderItemsRequest = new ArrayList<>();
		OrderItemRequestDTO orderItemRequest = new OrderItemRequestDTO(itemResponse.id(), orderItemQuantity);
		orderItemsRequest.add(orderItemRequest);

		OrderRequestDTO orderRequest = new OrderRequestDTO(memberResponse.id(), orderItemsRequest);

		int prevStock = itemResponse.stock();

		// when
		// 주문 생성

		orderService.requestOrderProcess(new OrderRequestDTO(memberResponse.id(), orderItemsRequest));

		itemResponse = itemService.requestGetItem(itemResponse.id());

		int curStock = itemResponse.stock();
		// then
		// 재고 감소 확인
		assertThat(curStock).isEqualTo(prevStock - orderItemQuantity);
	}

	@Test
	@DisplayName("주문 취소 시 재고 복구")
	void RestoreStockCancelOrder(){
		// given
		// 회원, 아이템, 주문
		String memberName = "홍길동";
		String memberEmail = "Hong@gmail.com";

		MemberRequestDTO memberRequest = new MemberRequestDTO(memberName, memberEmail);
		MemberResponseDTO memberResponse = memberService.requestRegisterMember(memberRequest);

		String itemName = "싱싱한 딸기";
		Integer itemPrice = 5000;
		Integer itemStock = 40;

		ItemRequestDTO itemRequest = new ItemRequestDTO(itemName, itemPrice, itemStock);
		ItemResponseDTO itemResponse = itemService.requestRegisterItem(itemRequest);

		Integer orderItemQuantity = 10;

		List<OrderItemRequestDTO> orderItemsRequest = new ArrayList<>();
		OrderItemRequestDTO orderItemRequest = new OrderItemRequestDTO(itemResponse.id(), orderItemQuantity);
		orderItemsRequest.add(orderItemRequest);

		OrderRequestDTO orderRequest = new OrderRequestDTO(memberResponse.id(), orderItemsRequest);

		int initStock = itemResponse.stock();

		// when
		// 주문 생성
		// 주문 취소

		OrderResponseDTO orderResponse = orderService.requestOrderProcess(new OrderRequestDTO(memberResponse.id(), orderItemsRequest));
		itemResponse = itemService.requestGetItem(itemResponse.id());

		int decreasedStock = itemResponse.stock();

		orderService.requestDeleteOrder(orderResponse.id());
		itemResponse = itemService.requestGetItem(itemResponse.id());

		int restoredStock = itemResponse.stock();

		// then
		// 재고 감소 확인
		// 재고 복구 확인
		assertThat(decreasedStock).isEqualTo(initStock - orderItemQuantity);
		assertThat(restoredStock).isEqualTo(decreasedStock + orderItemQuantity);
	}
}
