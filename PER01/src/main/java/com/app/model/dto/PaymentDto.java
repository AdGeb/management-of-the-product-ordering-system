package com.app.model.dto;

import com.app.dao.generic.DbTables;
import com.app.model.EPayment;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class PaymentDto {
    private Long id;
    private EPayment ePayment;
    private Set<CustomerOrderDto> paymentHistory = new HashSet<>();

    public PaymentDto(){}

    public PaymentDto(Long id, EPayment ePayment, Set<CustomerOrderDto> paymentHistory) throws Exception {
        setId(id);
        setePayment(ePayment);
        setPaymentHistory(paymentHistory);
    }

    public PaymentDto(EPayment ePayment, Set<CustomerOrderDto> paymentHistory) throws Exception {
        setePayment(ePayment);
        setPaymentHistory(paymentHistory);
    }

    public void setId(Long id) throws Exception {
        if(id != null && id > 0L) {
            this.id = id;
        }else throw new Exception(DbTables.Payment + ";TRIED TO SET INCORRECT ID = " + id);
    }

    public void setePayment(EPayment ePayment) throws Exception {
        if(ePayment != null) {
            this.ePayment = ePayment;
        }else throw new Exception(DbTables.Payment + ";TRIED TO SET ePAYMENT = NULL");
    }

    public void setPaymentHistory(Set<CustomerOrderDto> paymentHistory) throws Exception {
        if(paymentHistory != null){
            this.paymentHistory = paymentHistory;
        }else throw new Exception(DbTables.Payment + ";TRIED TO SET PAYMENT HISTORY = NULL");
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "id=" + id +
                ", ePayment=" + ePayment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PaymentDto that = (PaymentDto) o;
        return Objects.equals(id, that.id) &&
                ePayment == that.ePayment;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, ePayment);
    }
}
