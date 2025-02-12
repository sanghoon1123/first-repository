package sanghoonbook.sanghoonshop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import sanghoonbook.sanghoonshop.domain.OrderItem;


@Getter
public class OrderItemDto {
    private String itemName;
    private int orderPrice;
    private int count;



    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }

    @QueryProjection
    public OrderItemDto(String itemName, int orderPrice, int count) {
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
