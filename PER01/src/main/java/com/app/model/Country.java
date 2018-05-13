package com.app.model;

import com.app.dao.*;
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
@Entity
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "countryName"))
public class Country {
    @Id
    @GeneratedValue
    private Long id;

    private String countryName;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    Set<Customer> customers = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    Set<Producer> producers = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "country")
    private Set<Shop> shops = new HashSet<>();

    public void addShops(Shop ... shopsToInsert){
        if(shopsToInsert != null){
            ShopDao shopDao = new ShopDaoImpl();
            for (Shop s : shopsToInsert) {
                //s.setCountry(this);
                shopDao.update(s);
                shops.add(s);
            }
        }
    }

    public void addProducers(Producer ... producersToInsert){
        if(producersToInsert != null){
            ProducerDao producerDao = new ProducerDaoImpl();
            for (Producer c : producersToInsert) {
                producers.add(c);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(countryName, country.countryName) &&
                Objects.equals(customers, country.customers) &&
                Objects.equals(producers, country.producers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, countryName);
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
