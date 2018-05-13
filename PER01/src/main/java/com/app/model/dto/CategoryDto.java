package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(){}

    public CategoryDto(Long id, String name) throws Exception {
        setId(id);
        setName(name);
    }

    public CategoryDto(String name) throws Exception {
        setName(name);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Category + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setName(String name) throws Exception {
        if(name != null && name.matches("([A-Z]*\\s*)+")){
            this.name = name;
        } else throw new Exception(DbTables.Category + ";TRIED TO SET CATEGORYDTO'S NAME = " + name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CategoryDto that = (CategoryDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
