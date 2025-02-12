package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Album extends Item{
    private String artist;
    private String ect;

    public Album(String name, int price, int stockQuantity, String artist, String ect) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.ect = ect;
    }

    protected Album(){

    }
}
