package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public void setQuantity(Integer quantity) {
        if(quantity >= 0){
            this.quantity = quantity;
        }else System.out.println("QUANTITY CANNOT BE NEGATIVE!");
    }

    public void deleteOrderedProducts(int amount){
        if(amount > quantity){
            System.out.println("NOT ENOUGH PRODUCTS IN THIS STOCK!");
            return;
        }
        this.setQuantity(this.quantity - amount);
    }
}
