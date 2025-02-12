package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }

    protected Delivery() {
    }



    public void setOrder(Order order){
        this.order = order;
        if(order != null){
            order.setDelivery(this);
        }
    }
}
