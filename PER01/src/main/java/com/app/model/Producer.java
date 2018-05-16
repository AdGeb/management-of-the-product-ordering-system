package com.app.model;

import com.app.dao.ProductDao;
import com.app.dao.ProductDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne (cascade =CascadeType.PERSIST)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "producer")
    Set<Product> products = new HashSet<>();



    public boolean checkNameCountryTrade(Producer p){
        if(p.getName().equals(name) && p.getCountry().getCountryName().equals(country.getCountryName()) && p.getTrade().getName().equals(trade.getName())){
            return true;
        }
        return false;
    }

    public void addProducts(Product ... productsToInsert) {
        if(productsToInsert != null){
            ProductDao productDao = new ProductDaoImpl();
            for (Product p: productsToInsert) {
                    p.setProducer(this);
                    productDao.update(p);
                    products.add(p);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(country, producer.country) &&
                Objects.equals(trade, producer.trade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, country, trade);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }

    public String getName() {
        return name;
    }
}
