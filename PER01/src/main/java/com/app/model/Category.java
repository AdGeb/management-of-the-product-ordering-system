package com.app.model;

import com.app.dao.ProductDao;
import com.app.dao.ProductDaoImpl;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "category")
    private Set<Product> products = new HashSet<>();


    public void addProducts(Product ... productsToInsert){
        if(productsToInsert != null){
            for (Product p: productsToInsert) {
                p.setCategory(this);
                products.add(p);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
