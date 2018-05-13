package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private ProducerDto producerDto;
    private CategoryDto categoryDto;
    private Set<StockDto> stocksDto = new HashSet<>();

    public ProductDto(){}

    public ProductDto(Long id, String name, BigDecimal price, ProducerDto producerDto, CategoryDto categoryDto) throws Exception {
        setId(id);
        setName(name);
        setPrice(price);
        setProducerDto(producerDto);
        setCategoryDto(categoryDto);
    }

    public ProductDto(String name, BigDecimal price, ProducerDto producerDto, CategoryDto categoryDto) throws Exception {
        setName(name);
        setPrice(price);
        setProducerDto(producerDto);
        setCategoryDto(categoryDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Product + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if (name != null && name.matches("([A-Z]*[0-9]*\\s*)+")) {
            this.name = name;
        } else throw new Exception(DbTables.Product + ";TRIED TO SET INCORRECT PRODUCT'S NAME = " + name)  ;
    }

    public void setPrice(BigDecimal price) throws Exception {
        if (price.compareTo(new BigDecimal("0")) > 0) {
            this.price = new BigDecimal(price.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else throw new Exception(DbTables.Product + ";TRIED TO SET INCORRECT PRODUCT'S PRICE = " + price.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }

    public void setProducerDto(ProducerDto producerDto) throws Exception {
        if(producerDto != null){
            this.producerDto = producerDto;
        }else throw new Exception(DbTables.Product + ";TRIED TO SET PRODUCT'S PRODUCER AS NULL");
    }

    public void setCategoryDto(CategoryDto categoryDto) throws Exception {
        if(categoryDto != null){
            this.categoryDto = categoryDto;
        }else throw new Exception(DbTables.Product + ";TRIED TO SET PRODUCT'S CATEGORY AS NULL");
    }

    public void addStocksDto(List<StockDto> stockDto) throws Exception {
        if(stockDto != null){
            for (StockDto s: stockDto) {
                stocksDto.add(s);
                s.setProductDto(this);
            }
        }
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", producerDto=" + producerDto +
                ", categoryDto=" + categoryDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(producerDto, that.producerDto) &&
                Objects.equals(categoryDto, that.categoryDto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, price, producerDto, categoryDto);
    }
}
