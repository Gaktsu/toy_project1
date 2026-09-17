package com.toyproject.shoppingManage;

import com.toyproject.shoppingManage.Item.ItemRepository;
import com.toyproject.shoppingManage.Item.ItemResponseDTO;
import com.toyproject.shoppingManage.Item.ItemService;
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
		// 기본 입력 검증
		// member 중복 검사
		assertThat(response).isNotNull();
		assertThat(response.id()).isNotNull();
		assertThat(response.email()).isEqualTo("Hong@naver.com");
	}

	@Test
	@DisplayName("주문 생성 시 재고 감소한다")
	void DecreseStockAfterOrder(){
		// given
		// 회원, 아이템, 주문 개
;
		Long member_id = 1L;
		Long item_id = 1L;
		int quantity = 100;

		int prevStock = itemService.requestGetItem(item_id).stock();

		List<OrderItemRequestDTO> orderItems = new ArrayList<>();
		orderItems.add(new OrderItemRequestDTO(item_id, quantity));

		// when
		// 주문 생성

		orderService.requestOrderProcess(new OrderRequestDTO(member_id, orderItems));

		int curStock = itemService.requestGetItem(item_id).stock();

		// then
		// 재고 감소 확인
		assertThat(prevStock).isEqualTo(curStock + quantity);
	}

	@Test
	@DisplayName("주문 취소 시 재고 복구")
	void RestoreStockCancelOrder(){
		// given
		// 주문

		Long order_id = 1L;

		OrderResponseDTO response = orderService.requestGetOrder(order_id);

 		// when
		// 주문 취소

		List<OrderItemResponseDTO> itemResponse = response.items();
		List<Integer> expectedRestoreStock = new ArrayList<>();

		for(var item : itemResponse){
			expectedRestoreStock.add(itemService.requestGetItem(item.itemId()).stock() + item.quantity());
		}

		orderService.requestDeleteOrder(order_id);

		// then
		// 재고 복구 확인
		for(int i = 0; i < itemResponse.size(); i++){
			int stock = itemService.requestGetItem(itemResponse.get(i).itemId()).stock();
			int expectedStock = expectedRestoreStock.get(i);
			assertThat(stock).isEqualTo(expectedStock);
		}
	}
}
