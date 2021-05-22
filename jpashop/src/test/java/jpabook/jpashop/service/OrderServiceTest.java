package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 jap", 10000, 10);

        int orderCount = 2;
        //when
        final Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        final Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품종류 수가 정확해야한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야한다.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강북", "123-123"));
        em.persist(member);
        return member;
    }


    @Test
    public void 주문취소() throws Exception {
        final Member member = createMember();
        final Book item = createBook("시골 jpa", 10000, 10);

        int orderCount = 2;

        final Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        final Order getOrder = orderRepository.findOne(orderId);


        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
        Assertions.assertThat(item.getStockQuantity()).isEqualTo(10);
    }


    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        final Member member = createMember();
        final Item item = createBook("시골 jap", 10000, 10);
        //when

        int orderCount = 11;
        Assertions.assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount));
        //then
    }

}