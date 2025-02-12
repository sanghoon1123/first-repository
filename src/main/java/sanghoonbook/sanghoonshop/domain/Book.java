package sanghoonbook.sanghoonshop.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;

    public Book(String name, int price, int stockQuantity, String author, String isbn) {
        super(name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }

    protected Book(){

    }
}
