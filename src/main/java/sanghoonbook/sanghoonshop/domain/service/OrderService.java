package sanghoonbook.sanghoonshop.domain.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sanghoonbook.sanghoonshop.domain.*;
import sanghoonbook.sanghoonshop.dto.OrderDto;
import sanghoonbook.sanghoonshop.dto.OrderItemDto;
import sanghoonbook.sanghoonshop.dto.QOrderDto;
import sanghoonbook.sanghoonshop.dto.QOrderItemDto;
import sanghoonbook.sanghoonshop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.*;



@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EntityManager em;

    public Order save(Order order){
        return orderRepository.save(order);
    }

    public Order findOne(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

    }

    public void cancelOrder(Long id){
        Order findOrder = findOne(id);
        findOrder.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        QMember member = QMember.member;
        QOrder order = QOrder.order;
       

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();

        if(orderSearch.getOrderStatus() != null){
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }

        if (StringUtils.hasText(orderSearch.getMemberName())){
            builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
        }

        return queryFactory.select(order)
                .from(order)
                .join(order.member, member).fetchJoin()
                .where(builder)
                .limit(1000)
                .fetch();

    }

    public Page<Order> findOrders2(OrderSearch orderSearch, int page, int size){
        QMember member = QMember.member;
        QOrder order = QOrder.order;


        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();

        if(orderSearch.getOrderStatus() != null){
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }

        if (StringUtils.hasText(orderSearch.getMemberName())){
            builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
        }

        Long total = queryFactory.select(order.count())
                .from(order)
                .join(order.member, member)
                .where(builder)
                .fetchOne();

        List<Order> content = queryFactory.select(order)
                .from(order)
                .join(order.member, member).fetchJoin()
                .where(builder)
                .offset(page * size)
                .limit(size)
                .fetch();

        return new PageImpl<>(content, PageRequest.of(page, size), total);

    }

    public Page<OrderDto> findOrders3(OrderSearch orderSearch, int page, int size) {
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        QDelivery delivery = QDelivery.delivery;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();

        if (orderSearch.getOrderStatus() != null) {
            builder.and(order.status.eq(orderSearch.getOrderStatus()));
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
        }

        Long total = queryFactory.select(order.count())
                .from(order)
                .join(order.member, member)
                .where(builder)
                .fetchOne();

        List<OrderDto> content = queryFactory
                .select(new QOrderDto(order.id, member.name, order.orderDate, order.status, order.delivery.address))
                .from(order)
                .join(order.member, member)
                .join(order.delivery, delivery)
                .where(builder)
                .offset(page * size)
                .limit(size)
                .fetch();

        return new PageImpl<>(content, PageRequest.of(page, size), total); // 반환 타입 일치
    }
}
