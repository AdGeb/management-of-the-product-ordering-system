package com.app.model;

import com.app.dao.generic.DbTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime date;
    private BigDecimal discount;
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;


    public void setQuantity(Integer quantity) throws Exception {
        if(quantity == null || product.getQuantityFromStocks() == null || product.getQuantityFromStocks() < quantity){
            System.out.println("////////////////COULDN'T COMPLETE ORDER!////////////////");
            System.out.println("PRODUCT: " + product.getName());
            System.out.println("NOT ENOUGH PRODUCTS IN STOCK!\nDESIRED QUANTITY: " + quantity + "\nPOSSIBLE MAX QUANTITY: " + product.getQuantityFromStocks());
            throw new Exception(DbTables.Customer_Order + ";TRIED TO ORDER MORE THAN IS POSSIBLE DESIRED: " + quantity + ", POSSIBLE: " + product.getQuantityFromStocks() + " OF PRODUCT = " + getProduct().getName());
        }else {
            product.sellXProducts(quantity);
            this.quantity = quantity;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerOrder that = (CustomerOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(payment, that.payment) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, date, discount, quantity, customer, payment, product);
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", date=" + date +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", customer=" + customer +
                ", payment=" + payment +
                ", product=" + product +
                '}';
    }
}
