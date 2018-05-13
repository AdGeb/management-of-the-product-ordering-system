package com.app.model.dto;

import com.app.dao.generic.DbTables;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class CustomerOrderDto {
    private Long id;
    private LocalDateTime date;
    private BigDecimal discount;
    private Integer quantity;
    private CustomerDto customerDto;
    private PaymentDto paymentDto;
    private ProductDto productDto;

    public CustomerOrderDto() { }

    public CustomerOrderDto(Long id, LocalDateTime date, BigDecimal discount, Integer quantity, CustomerDto customerDto, PaymentDto paymentDto, ProductDto productDto) throws Exception {
        setId(id);
        setDate(date);
        setDiscount(discount);
        setCustomerDto(customerDto);
        setProductDto(productDto);
        setPaymentDto(paymentDto);
        setQuantity(quantity);
    }

    public CustomerOrderDto(LocalDateTime date, BigDecimal discount, Integer quantity, CustomerDto customerDto, PaymentDto paymentDto, ProductDto productDto) throws Exception {
        setDate(date);
        setDiscount(discount);
        setCustomerDto(customerDto);
        setProductDto(productDto);
        setPaymentDto(paymentDto);
        setQuantity(quantity);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Customer_Order + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setDate(LocalDateTime date) throws Exception {
        if(date == null ){
            System.out.println(date);
            throw new Exception(DbTables.Customer_Order + ";TRIED TO PLACE ORDER WITH INCORRECT DATE");
        }
        this.date = date;
    }

    public void setQuantity(Integer quantity) throws Exception {
        if(quantity != null && quantity >= 0) {
            this.quantity = quantity;
        }else throw new Exception(DbTables.Customer_Order + ";TRIED TO ORDER <=0 PRODUCTS");
    }

    public void setDiscount(BigDecimal discount) throws Exception {
        if(discount != null && (new BigDecimal("100")).subtract(discount).intValue() >= 0 &&  (new BigDecimal("100")).subtract(discount).intValue() <= 100) {
            this.discount = discount;
        }else throw new Exception(DbTables.Customer_Order + ";TRIED TO APPLY DISCOUNT = " + discount);
    }

    public void setCustomerDto(CustomerDto customerDto) throws Exception {
        if(customerDto != null){
            this.customerDto = customerDto;
        } else throw new Exception(DbTables.Customer_Order + ";TRIED TO ADD NULL CUSTOMERDTO");
    }

    public void setPaymentDto(PaymentDto paymentDto) throws Exception {
        if(paymentDto != null){
            this.paymentDto = paymentDto;
        } else throw new Exception(DbTables.Customer_Order + ";TRIED TO ADD NULL PAYMENTDTO");
    }

    public void setProductDto(ProductDto productDto) throws Exception {
        if(productDto != null){
            this.productDto = productDto;
        } else throw new Exception(DbTables.Customer_Order + ";TRIED TO ADD NULL PRODUCTDTO");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomerOrderDto that = (CustomerOrderDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(customerDto, that.customerDto) &&
                Objects.equals(paymentDto, that.paymentDto) &&
                Objects.equals(productDto, that.productDto);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, date, discount, quantity, customerDto, paymentDto, productDto);
    }

    @Override
    public String toString() {
        return "CustomerOrderDto{" +
                "id=" + id +
                ", date=" + date +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", customerDto=" + customerDto +
                ", paymentDto=" + paymentDto +
                ", productDto=" + productDto +
                '}';
    }
}
