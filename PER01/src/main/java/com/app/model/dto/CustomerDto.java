package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private CountryDto countryDto;
    private Set<CustomerOrderDto> customerOrdersDto = new HashSet<>();

    public CustomerDto() {}

    public CustomerDto(Long id, String name, String surname, Integer age, CountryDto countryDto, Set<CustomerOrderDto> customerOrdersDto) throws Exception {
        setId(id);
        setName(name);
        setSurname(surname);
        setAge(age);
        setCountryDto(countryDto);
        setCustomerOrdersDto(customerOrdersDto);
    }

    public CustomerDto(String name, String surname, Integer age, CountryDto countryDto, Set<CustomerOrderDto> customerOrdersDto) throws Exception {
        setName(name);
        setSurname(surname);
        setAge(age);
        setCountryDto(countryDto);
        setCustomerOrdersDto(customerOrdersDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Customer + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if(name != null && name.matches("^[A-Z\\s]+?")){
            this.name = name;
        }else throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH INCORRECT NAME = " + name);
    }

    public void setSurname(String surname) throws Exception {
        if(surname != null && surname.matches("^[A-Z\\s]+?")){
            this.surname = surname;
        }else throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH INCORRECT SURNAME = " + surname);
    }

    public void setAge(Integer age) throws Exception {
        if(age != null && age >= 18){
            this.age = age;
        }else {
            throw new Exception(DbTables.Customer + ";TRIED TO ADD CUSTOMER WITH AGE < 18");
        }
    }

    public void setCountryDto(CountryDto countryDto) throws Exception {
        if(countryDto != null) {
            this.countryDto = countryDto;
        }else throw new Exception(DbTables.Customer + ";TRIED TO SET CUSTOMERDTO'S COUNTRY AS NULL!)");
    }

    public void setCustomerOrdersDto(Set<CustomerOrderDto> customerOrdersDto) throws Exception {
        if(customerOrdersDto != null){
            this.customerOrdersDto = customerOrdersDto;
        } else throw new Exception(DbTables.Customer + ";TRIED TO SET CUSTOMERDTO'S CUSTOMERORDERS AS NULL!)");
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", countryDto=" + countryDto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(age, that.age) &&
                Objects.equals(countryDto, that.countryDto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name, surname, age, countryDto);
    }
}
