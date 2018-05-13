package com.app.model.dto;


import com.app.dao.generic.DbTables;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class CountryDto {
    private Long id;
    private String countryName;
    private Set<CustomerDto> customersDto = new HashSet<>();
    private Set<ProducerDto> producersDto = new HashSet<>();
    private Set<ShopDto> shopsDto = new HashSet<>();

    public CountryDto() { }

    public CountryDto(Long id, String countryName, Set<CustomerDto> customersDto, Set<ProducerDto> producersDto, Set<ShopDto> shopsDto) throws Exception {
        setId(id);
        setCountryName(countryName);
        setCustomersDto(customersDto);
        setProducersDto(producersDto);
        setShopsDto(shopsDto);
    }

    public CountryDto(String countryName, Set<CustomerDto> customersDto, Set<ProducerDto> producersDto, Set<ShopDto> shopsDto) throws Exception {
        setCountryName(countryName);
        setCustomersDto(customersDto);
        setProducersDto(producersDto);
        setShopsDto(shopsDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Country + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setCountryName(String countryName) throws Exception {
        if(countryName != null && countryName.matches("^[A-Z\\s]+?")) {
            this.countryName = countryName;
        }else throw new Exception(DbTables.Country + ";TRIED TO CREATE COUNTRY WITH INCORRECT NAME = " + countryName);
    }

    public void setCustomersDto(Set<CustomerDto> customersDto) throws Exception {
        if(customersDto != null){
            this.customersDto = customersDto;
        } else throw new Exception(DbTables.Country + ";TRIED TO SET COUNTRY'S CUSTOMERS AS NULL");
    }

    public void setProducersDto(Set<ProducerDto> producersDto) throws Exception {
        if(producersDto != null){
            this.producersDto = producersDto;
        } else throw new Exception(DbTables.Country + ";TRIED TO SET COUNTRY'S PRODUCERS AS NULL");
    }

    public void setShopsDto(Set<ShopDto> shopsDto) throws Exception {
        if(shopsDto != null){
            this.shopsDto = shopsDto;
        } else throw new Exception(DbTables.Country + ";TRIED TO SET COUNTRY'S SHOPS AS NULL");
    }

    public void addCustomersDto(CustomerDto ... customersDtoToInsert){
        if(customersDtoToInsert != null){
            customersDto.addAll(Arrays.asList(customersDtoToInsert));
        }
    }

    public void addShopsDto(ShopDto ... shopsDtoToInsert){
        if(shopsDtoToInsert != null){
            shopsDto.addAll(Arrays.asList(shopsDtoToInsert));
        }
    }

    public void addProducersDto(ProducerDto ... producersDtoToInsert){
        if(producersDtoToInsert != null){
            producersDto.addAll(Arrays.asList(producersDtoToInsert));
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CountryDto that = (CountryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(countryName, that.countryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, countryName);
    }

    @Override
    public String toString() {
        return "CountryDto{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
