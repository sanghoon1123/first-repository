package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipCode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipCode;
    }

    protected Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipcode + '\'' +
                '}';
    }
}
