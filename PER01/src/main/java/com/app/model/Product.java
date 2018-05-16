package com.app.model;

import com.app.dao.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class    Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "product")
    private Set<CustomerOrder> customer_order = new HashSet<>();

    @ElementCollection

    @CollectionTable(
            name = "guarantee_component",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "guarantee_component")
    private Set<EGuarantee> eGuarantees = new HashSet<>();


    public static EGuarantee recognizeEGuarantee(String s){
        if(s.equals("EXCHANGE")) return EGuarantee.EXCHANGE;
        if(s.equals("HELP DESK")) return EGuarantee.HELP_DESK;
        if(s.equals("MONEY BACK")) return EGuarantee.MONEY_BACK;
        if(s.equals("SERVICE")) return EGuarantee.SERVICE;
        return null;
    }

    public void addGuarantee(EGuarantee eGuarantee){
        if(!eGuarantees.contains(eGuarantee)){
            eGuarantees.add(eGuarantee);
            Set<EGuarantee> temp = eGuarantees;
            setEGuarantees(new HashSet<>());
            eGuarantees = temp;
        }
    }

    public void addGuarantees(EGuarantee ... eGuarantees){
        if(eGuarantees != null){
            for (EGuarantee e: eGuarantees) {
                addGuarantee(e);
            }
        }
    }

    public void deleteGuarantee(EGuarantee eGuarantee){
        if(eGuarantees.contains(eGuarantee)){
            eGuarantees.remove(eGuarantee);
        }
    }

    public void addStocks(List<Stock> stocksToInsert) {
        if (stocksToInsert != null) {
            StockDao stockDao = new StockDaoImpl();
            for (Stock s : stocksToInsert) {
                s.setProduct(this);
                stockDao.update(s);
                stocks.add(s);
            }
        }
    }

    public Integer getNumberOfSoldProducts(){
        Integer sum = new Integer(0);
        for (CustomerOrder co : this.getCustomer_order()) {
            sum += co.getQuantity();
        }
        return sum;
    }

    public Integer getQuantityFromStocks() {
        Integer sum = new Integer(0);
        for (Stock s : this.getStocks()) {
            sum += s.getQuantity();
        }
        return sum;
    }

    public void sellXProducts(Integer amount) {
        if (getQuantityFromStocks() < amount) {
            System.out.println("NOT ENOUGH PRODUCTS IN STOCKS!");
            return;
        } else {
            StockDao stockDao = new StockDaoImpl();
            for (Stock s : this.getStocks()) {
                if (s.getQuantity() >= amount) {
                    int temp = s.getQuantity();
                    s.deleteOrderedProducts(amount);
                    stockDao.update(s);
                    if(temp - amount > 0){
                        break;
                    } else amount -= temp;
                } else {
                    amount -= s.getQuantity();
                    s.setQuantity(0);
                    stockDao.update(s);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(producer, product.producer);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, price, producer);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Producer getProducer() {
        return producer;
    }
}
