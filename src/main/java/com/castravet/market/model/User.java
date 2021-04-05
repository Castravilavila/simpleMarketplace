package com.castravet.market.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_product",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinTable(name="user_role", joinColumns = {
            @JoinColumn(name="user_id", nullable=false,updatable=false)},inverseJoinColumns = {
            @JoinColumn(name="role_id", nullable=false, updatable = false)})
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object user){
        if(!(user instanceof User))return false;
        else{
            return ((User) user).getId() == this.getId();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + getId() +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}
