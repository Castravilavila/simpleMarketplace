package com.castravet.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ApiModel(description ="Details about the Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "the unique id of Product")
    private Long id;

    @ApiModelProperty(notes = "the title of product")
    private String title;
    @ApiModelProperty(notes = "the description of product")
    private String description;
    @ApiModelProperty(notes = "the price of product")
    private String price;

    @ApiModelProperty(notes = "how many users liked the product")
    private Integer likes = 0;
    @ApiModelProperty(notes = "how many users disliked the product")
    private Integer unlikes =0;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    private Set<User> users= new HashSet<>();

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
