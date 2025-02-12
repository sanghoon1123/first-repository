package sanghoonbook.sanghoonshop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import sanghoonbook.sanghoonshop.domain.Address;
import sanghoonbook.sanghoonshop.domain.Order;
import sanghoonbook.sanghoonshop.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;


    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
        System.out.println("📌 Order ID: " + orderId + " - OrderItems: " + order.getOrderItems());
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem)).collect(toList());
    }

    @QueryProjection
    public OrderDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
