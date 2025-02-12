package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void setMember(Member member){
        this.member = member;
        if (member != null) {
            member.getOrders().add(this);  // 양방향 관계 설정
        }
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        if(delivery != null){
            delivery.setOrder(this);
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem); // Order의 orderItems 리스트에 추가
        orderItem.setOrder(this);  // OrderItem에도 Order 설정 (양방향 관계 유지)
    }

    public Order(Member member, Delivery delivery, OrderItem... orderItems) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();

        for (OrderItem orderItem : orderItems){
            addOrderItem(orderItem);
        }
    }

    


    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    protected Order() {
    }
}
