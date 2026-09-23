package com.toyproject.shoppingManage;

import com.toyproject.shoppingManage.Item.*;
import com.toyproject.shoppingManage.Member.*;
import com.toyproject.shoppingManage.Member.Exception.DuplicateMemberException;
import com.toyproject.shoppingManage.Member.Exception.MemberNotFoundException;
import com.toyproject.shoppingManage.Order.*;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItem;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItemRequestDTO;
import com.toyproject.shoppingManage.Order.OrderItems.OrderItemResponseDTO;
import jakarta.persistence.EntityManager;
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

	@Autowired
	private EntityManager entityManager; // flush, clear 용도


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
		assertThatThrownBy(() -> memberService.requestRegisterMember(request)).isInstanceOf(DuplicateMemberException.class);
	}

	@Test
	@DisplayName("주문 생성 시 재고 감소한다")
	void DecreseStockAfterOrder(){
		// given
		// 회원, 아이템, 주문 개
;
		MemberResponseDTO memberResponse = helperMemberCreate();

		ItemResponseDTO itemResponse = helperItemCreate();

		Integer orderItemQuantity = 10;

		List<OrderItemRequestDTO> orderItemsRequest = new ArrayList<>();
		OrderItemRequestDTO orderItemRequest = new OrderItemRequestDTO(itemResponse.id(), orderItemQuantity);
		orderItemsRequest.add(orderItemRequest);

		OrderRequestDTO orderRequest = new OrderRequestDTO(memberResponse.id(), orderItemsRequest);

		int prevStock = itemResponse.stock();

		// when
		// 주문 생성

		OrderResponseDTO orderResponse = orderService.requestOrderProcess(new OrderRequestDTO(memberResponse.id(), orderItemsRequest));

		entityManager.flush();
		entityManager.clear();

		ItemResponseDTO test_DB_SELECT_AFTER_CLEAR = itemService.requestGetItem(itemResponse.id());
		int curStock = test_DB_SELECT_AFTER_CLEAR.stock();

		// then
		// 재고 감소 확인

		assertThat(curStock).isEqualTo(prevStock - orderItemQuantity);
	}

	@Test
	@DisplayName("주문 취소 시 재고 복구")
	void RestoreStockCancelOrder(){
		// given
		// 회원, 아이템, 주문

		MemberResponseDTO memberResponse = helperMemberCreate();

		ItemResponseDTO itemResponse = helperItemCreate();

		Integer orderItemQuantity = 10;
		OrderResponseDTO orderResponse = helperOrderCreate(memberResponse, itemResponse, orderItemQuantity);

		entityManager.flush();
		entityManager.clear();

		ItemResponseDTO test_DB_SELECT_BEFORE = itemService.requestGetItem(itemResponse.id());
		int initStock = test_DB_SELECT_BEFORE.stock();

		// when
		// 주문 취소

		orderService.requestDeleteOrder(orderResponse.id());

		entityManager.flush();
		entityManager.clear();

		ItemResponseDTO test_DB_SELECT_AFTER = itemService.requestGetItem(itemResponse.id());
		int restoredStock = test_DB_SELECT_AFTER.stock();

		// orderService.requestGetOrder(orderResponse.id()); --> 예상된 에러 발생 (존재하지 않는 주문입니다.)
		// then
		// 재고 감소 확인
		// 재고 복구 확인
		assertThat(restoredStock).isNotEqualTo(initStock);
	}

	@Test
	@DisplayName("상품 가격 및 재고 변경")
	void UpdateItemPrice_Stock(){
		// given
		// 아이템 하나 생성 및 등록

		ItemResponseDTO itemResponse = helperItemCreate();

		// when
		// 데이터 수정
		ItemUpdateRequestDTO itemUpdateRequest = new ItemUpdateRequestDTO(3000, null);
		itemResponse = itemService.requestUpdateItem_PATCH(itemResponse.id(), itemUpdateRequest);

		entityManager.flush();
		entityManager.clear();

		ItemResponseDTO test_DB_SELECT_AFTER = itemService.requestGetItem(itemResponse.id());
		// then
		// 데이터 수정 처리가 되었는지 검증
		assertThat(test_DB_SELECT_AFTER.price()).isEqualTo(3000);
		assertThat(test_DB_SELECT_AFTER.stock()).isEqualTo(100);
	}

	// ---------- Helper Method -------------

	MemberResponseDTO helperMemberCreate(){
		String memberName = "홍길동";
		String memberEmail = "Hong@gmail.com";

		MemberRequestDTO memberRequest = new MemberRequestDTO(memberName, memberEmail);

		return memberService.requestRegisterMember(memberRequest);
	}

	ItemResponseDTO helperItemCreate(){
		String name = "싱싱한 딸기";
		Integer price = 1500;
		Integer stock = 50;

		ItemRequestDTO itemRequest = new ItemRequestDTO(name, price, stock);

        return itemService.requestRegisterItem(itemRequest);
	}

	OrderResponseDTO helperOrderCreate(MemberResponseDTO memberResponse, ItemResponseDTO itemResponse, Integer orderItemQuantity){
		List<OrderItemRequestDTO> orderItemsRequest = new ArrayList<>();
		OrderItemRequestDTO orderItemRequest = new OrderItemRequestDTO(itemResponse.id(), orderItemQuantity);
		orderItemsRequest.add(orderItemRequest);

		OrderRequestDTO orderRequest = new OrderRequestDTO(memberResponse.id(), orderItemsRequest);

		return orderService.requestOrderProcess(new OrderRequestDTO(memberResponse.id(), orderItemsRequest));
	}
}
