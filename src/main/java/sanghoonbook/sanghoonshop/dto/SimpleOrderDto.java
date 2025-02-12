package sanghoonbook.sanghoonshop.dto;

import lombok.Data;
import sanghoonbook.sanghoonshop.domain.Address;
import sanghoonbook.sanghoonshop.domain.Order;
import sanghoonbook.sanghoonshop.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress();
    }
}
