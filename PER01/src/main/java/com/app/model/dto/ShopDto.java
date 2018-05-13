package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
public class ShopDto {
    private Long id;
    private String name;
    private CountryDto countryDto;

    public ShopDto(){}

    public ShopDto(Long id, String name, CountryDto countryDto) throws Exception {
        setId(id);
        setName(name);
        setCountryDto(countryDto);
    }

    public ShopDto(String name, CountryDto countryDto) throws Exception {
        setName(name);
        setCountryDto(countryDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Shop + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if(name != null && name.matches("([A-Z]*\\s*)+")){
            this.name = name;
        } else throw new Exception(DbTables.Shop + ";TRIED TO SET INCORRECT NAME = " + name);
    }

    public void setCountryDto(CountryDto countryDto) throws Exception {
        if(countryDto != null){
            this.countryDto = countryDto;
        }else throw new Exception(DbTables.Shop + ";TRIED TO SET SHOPDTO'S COUNTRY AS NULL");
    }

    @Override
    public String toString() {
        return "ShopDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryDto=" + countryDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ShopDto shopDto = (ShopDto) o;
        return Objects.equals(id, shopDto.id) &&
                Objects.equals(name, shopDto.name) &&
                Objects.equals(countryDto, shopDto.countryDto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, countryDto);
    }
}
