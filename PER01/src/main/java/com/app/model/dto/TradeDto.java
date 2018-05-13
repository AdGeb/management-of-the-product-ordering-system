package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class TradeDto {
    private Long id;
    private String name;
    private Set<ProducerDto> producersDto = new HashSet<>();

    public TradeDto(){}

    public TradeDto(Long id, String name, Set<ProducerDto> producersDto) throws Exception {
        setId(id);
        setName(name);
        setProducersDto(producersDto);
    }

    public TradeDto(String name, Set<ProducerDto> producersDto) throws Exception {
        setName(name);
        setProducersDto(producersDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Trade+ ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if(name != null && name.matches("([A-Z]*\\s*)+")){
            this.name = name;
        } else throw new Exception(DbTables.Trade + ";TRIED TO SET TRADEDTO'S NAME = " + name);
    }

    public void setProducersDto(Set<ProducerDto> producersDto) throws Exception {
        if(producersDto != null){
            this.producersDto = producersDto;
        }else throw new Exception(DbTables.Trade + ";TRIED TO SET TRADEDTO'S PRODUCERS AS NULL");
    }

    @Override
    public String toString() {
        return "TradeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TradeDto tradeDto = (TradeDto) o;
        return Objects.equals(id, tradeDto.id) &&
                Objects.equals(name, tradeDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name);
    }
}
