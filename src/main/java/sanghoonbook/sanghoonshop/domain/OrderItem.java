package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "order_item")
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem(item, orderPrice, count);
        item.removeStock(count);

        return orderItem;

    }

    public void setOrder(Order order) {
        this.order = order;

    }

    public void setItem(Item item) {
        this.item = item;
        if (item != null) {
            item.getOrderItems().add(this);  // 양방향 관계 설정
        }
    }

    protected OrderItem() {
    }

    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }
}
