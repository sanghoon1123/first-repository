package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import sanghoonbook.sanghoonshop.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    private String name;
    private int price;
    private int stockQuantity;

    protected Item() {
    }

    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void update(String name, int price, int stockQuantity){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        if (orderItem != null) {
            orderItem.setItem(this);  // 양방향 관계 설정
        }
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
