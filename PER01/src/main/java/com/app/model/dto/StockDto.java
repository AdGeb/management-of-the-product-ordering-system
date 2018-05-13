package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
public class StockDto {
    private Long id;
    private Integer quantity;
    private ProductDto productDto;
    private ShopDto shopDto;

    public StockDto(){}

    public StockDto(Long id, Integer quantity, ProductDto productDto, ShopDto shopDto) throws Exception {
        setId(id);
        setQuantity(quantity);
        setProductDto(productDto);
        setShopDto(shopDto);
    }

    public StockDto(Integer quantity, ProductDto productDto, ShopDto shopDto) throws Exception {
        setQuantity(quantity);
        setProductDto(productDto);
        setShopDto(shopDto);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Stock + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setQuantity(Integer quantity) throws Exception {
        if(quantity >= 0){
            this.quantity = quantity;
        }else throw new Exception(DbTables.Stock + ";TRIED TO SET INCORRECT STOCKDTO'S QUANTITY = " + quantity);
    }

    public void setProductDto(ProductDto productDto) throws Exception {
        if(productDto != null){
            this.productDto = productDto;
        }else throw new Exception(DbTables.Stock + ";TRIED TO SET STOCKDTO's PRODUCT AS NULL");
    }

    public void setShopDto(ShopDto shopDto) throws Exception {
        if(shopDto != null){
            this.shopDto = shopDto;
        }else throw new Exception(DbTables.Stock + ";TRIED TO SET STOCKDTO's SHOP AS NULL");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StockDto stockDto = (StockDto) o;
        return Objects.equals(id, stockDto.id) &&
                Objects.equals(quantity, stockDto.quantity) &&
                Objects.equals(productDto, stockDto.productDto) &&
                Objects.equals(shopDto, stockDto.shopDto);
    }

    @Override
    public String toString() {
        return "StockDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productDto=" + productDto +
                ", shopDto=" + shopDto +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, quantity, productDto, shopDto);
    }
}
