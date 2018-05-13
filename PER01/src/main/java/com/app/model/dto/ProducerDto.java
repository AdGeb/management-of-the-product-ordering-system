package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class ProducerDto {
    private Long id;
    private String name;
    private CountryDto countryDto;
    private TradeDto tradeDto;
    private Set<ProductDto> productsDto = new HashSet<>();

    public ProducerDto(){}

    public ProducerDto(Long id, String name, CountryDto countryDto, TradeDto tradeDto, Set<ProductDto> productsDto) throws Exception {
        setId(id);
        setName(name);
        setCountryDto(countryDto);
        setTradeDto(tradeDto);
        setProductsDto(productsDto);
    }

    public ProducerDto(String name, CountryDto countryDto, TradeDto tradeDto, Set<ProductDto> productsDto) throws Exception {
        setName(name);
        setCountryDto(countryDto);
        setTradeDto(tradeDto);
        setProductsDto(productsDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Producer + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if(name != null && name.matches("([A-Z]*\\s*)+")){
            this.name = name;
        } else throw new Exception(DbTables.Producer + ";TRIED TO SET PRODUCER'S NAME LIKE = " + name);
    }

    public void setCountryDto(CountryDto countryDto) throws Exception {
        if(countryDto != null) {
            this.countryDto = countryDto;
        }else throw new Exception(DbTables.Producer + ";TRIED TO SET PRODUCER'S COUNTRY AS NULL");
    }

    public void setTradeDto(TradeDto tradeDto) throws Exception {
        if(tradeDto != null){
            this.tradeDto = tradeDto;
        }else throw new Exception(DbTables.Producer + ";TRIED TO SET PRODUCER'S TRADE AS NULL");
    }

    public void setProductsDto(Set<ProductDto> productsDto) throws Exception {
        if(productsDto != null){
            this.productsDto = productsDto;
        }else throw new Exception(DbTables.Producer + ";TRIED TO SET PRODUCER'S PRODUCTS AS NULL");
    }

    @Override
    public String toString() {
        return "ProducerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryDto=" + countryDto +
                ", tradeDto=" + tradeDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProducerDto that = (ProducerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(countryDto, that.countryDto) &&
                Objects.equals(tradeDto, that.tradeDto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, countryDto, tradeDto);
    }
}
