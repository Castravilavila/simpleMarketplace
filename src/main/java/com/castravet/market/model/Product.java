package com.castravet.market.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String price;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Override
    public boolean equals(Object product){
        if(!(product instanceof User))return false;
        else{
            return ((Product) product).getId() == this.getId();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + getId() +
                ", title='" + getTitle() + '\'' +
                '}';
    }
}
