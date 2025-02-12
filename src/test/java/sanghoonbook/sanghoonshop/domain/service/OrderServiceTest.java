package sanghoonbook.sanghoonshop.domain.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sanghoonbook.sanghoonshop.domain.*;
import sanghoonbook.sanghoonshop.exception.NotEnoughStockException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;
    @Autowired
    EntityManager em;

    @Test
    void save(){
        Address address = new Address("서울", "은천로", "15길24");

        Member member = new Member("상훈", address);
        memberService.saveMember(member);

        int stock = 100;
        Item item = new Item("과자", 3000, stock);
        itemService.saveItem(item);

        int orderCount = 3;
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        Order order = new Order(member,delivery, orderItem);
        orderService.save(order);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(3000*3);
        assertThat(item.getStockQuantity()).isEqualTo(97);
    }

    @Test
    void stockFail(){
        Address address = new Address("서울", "은천로", "15길24");

        Member member = new Member("상훈", address);
        memberService.saveMember(member);

        int stock = 100;
        Item item = new Item("과자", 3000, stock);
        itemService.saveItem(item);

        int orderCount = 101;


        assertThrows(NotEnoughStockException.class, () -> OrderItem.createOrderItem(item, item.getPrice(), orderCount), "need more stock");
    }

    @Test
    void cancel(){
        Address address = new Address("서울", "은천로", "15길24");

        Member member = new Member("상훈", address);
        memberService.saveMember(member);

        int stock = 100;
        Item item = new Item("과자", 3000, stock);
        Item saveItem = itemService.saveItem(item);

        int orderCount = 3;
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        Order order = new Order(member,delivery, orderItem);
        Order saveOrder = orderService.save(order);

        orderService.cancelOrder(saveOrder.getId());
        assertThat(saveOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(saveItem.getStockQuantity()).isEqualTo(100);
    }

    @Test
    void cancelFail(){
        Address address = new Address("서울", "은천로", "15길24");

        Member member = new Member("상훈", address);
        memberService.saveMember(member);

        int stock = 100;
        Item item = new Item("과자", 3000, stock);
        itemService.saveItem(item);

        int orderCount = 3;
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        Delivery delivery = new Delivery(address, DeliveryStatus.COMP);
        Order order = new Order(member,delivery, orderItem);
        Order saveOrder = orderService.save(order);


        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(saveOrder.getId()), "이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    @Test
    void orderSearch(){
        Address address1 = new Address("서울", "은천로", "15길24");
        Address address2 = new Address("부산", "해운대", "82길");

        Member member1 = new Member("상훈", address1);
        Member member2 = new Member("준혁", address2);
        memberService.saveMember(member1);
        memberService.saveMember(member2);

        int stock = 100;
        Item item = new Item("과자", 3000, stock);
        itemService.saveItem(item);

        int orderCount1 = 3;
        OrderItem orderItem1 = OrderItem.createOrderItem(item, item.getPrice(), orderCount1);

        int orderCount2 = 10;
        OrderItem orderItem2 = OrderItem.createOrderItem(item, item.getPrice(), orderCount2);

        Delivery delivery1 = new Delivery(address1, DeliveryStatus.READY);
        Delivery delivery2 = new Delivery(address2, DeliveryStatus.READY);

        Order order1 = new Order(member1,delivery1, orderItem1);
        Order order2 = new Order(member2,delivery2, orderItem2);
        orderService.save(order1);
        orderService.save(order2);



        OrderSearch searchByMember = new OrderSearch();
        searchByMember.setMemberName("상훈");

        List<Order> resultByMembers = orderService.findOrders(searchByMember);


        assertThat(resultByMembers.get(0).getMember().getName()).isEqualTo("상훈");

    }

}