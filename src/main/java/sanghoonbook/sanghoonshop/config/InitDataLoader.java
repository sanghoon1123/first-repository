package sanghoonbook.sanghoonshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import sanghoonbook.sanghoonshop.domain.*;
import sanghoonbook.sanghoonshop.domain.service.ItemService;
import sanghoonbook.sanghoonshop.domain.service.MemberService;
import sanghoonbook.sanghoonshop.domain.service.OrderService;
import sanghoonbook.sanghoonshop.repository.ItemRepository;
import sanghoonbook.sanghoonshop.repository.MemberRepository;
import sanghoonbook.sanghoonshop.repository.OrderRepository;

import java.util.List;

@Component
    @RequiredArgsConstructor
    public class InitDataLoader implements ApplicationRunner {

        private final MemberService memberService;
        private final ItemService itemService;
        private final OrderService orderService;
        private final MemberRepository memberRepository;
        private final OrderRepository orderRepository;
        private final ItemRepository itemRepository;

        @Override
        public void run(ApplicationArguments args) throws Exception {

            orderRepository.deleteAll();
            memberRepository.deleteAll();
            itemRepository.deleteAll();

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



        }
    }
