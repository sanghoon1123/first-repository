package sanghoonbook.sanghoonshop.domain;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String username;
    private String password;
    private String role;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    protected Member() {
    }

        public Member(String name, Address address) {
            this.name = name;
            this.address = address;
        }

    public Member(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
