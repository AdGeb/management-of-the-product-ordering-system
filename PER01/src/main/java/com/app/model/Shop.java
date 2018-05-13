package com.app.model;

import com.app.dao.StockDao;
import com.app.dao.StockDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "shop")
    private Set<Stock> stocks = new HashSet<>();
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;



    public  void addStocks(Stock ... stocksToInsert){
        if(stocksToInsert != null){
            StockDao stockDao = new StockDaoImpl();
            for (Stock s: stocksToInsert) {
                s.setShop(this);
                stockDao.update(s);
                stocks.add(s);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Shop shop = (Shop) o;
        return Objects.equals(id, shop.id) &&
                Objects.equals(name, shop.name) &&
                Objects.equals(country, shop.country);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, country);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                '}';
    }

    public Set<Stock> getStocks() {
        return stocks;
    }

    public boolean checkNameCountry(Shop shop){
        if(shop.getName().equals(this.getName()) && shop.getCountry().getCountryName().equals(this.getCountry().getCountryName())){
            return true;
        }
        return false;
    }
}
