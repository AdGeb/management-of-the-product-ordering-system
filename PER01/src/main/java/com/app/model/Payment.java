package com.app.model;

import com.app.dao.PaymentDao;
import com.app.dao.PaymentDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "payment"))
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private EPayment payment;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "payment")
    private Set<CustomerOrder> customer_order = new HashSet<>();

    public Payment(Long id, EPayment payment) {
        this.id = id;
        this.payment = payment;
    }

    public static void generate(boolean b){
        if (b){
            PaymentDao paymentDao = new PaymentDaoImpl();

            paymentDao.add(Payment.builder().payment(EPayment.CASH).build());
            paymentDao.add(Payment.builder().payment(EPayment.CARD).build());
            paymentDao.add(Payment.builder().payment(EPayment.MONEY_TRANSFER).build());

        }
    }

    public static EPayment recognizePaymentMethod(String payment){
        if(payment.equals("CASH")) return EPayment.CASH;
        if(payment.equals("CARD")) return EPayment.CARD;
        if(payment.equals("MONEY TRANSFER")) return EPayment.MONEY_TRANSFER;
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Payment payment1 = (Payment) o;
        return Objects.equals(id, payment1.id) &&
                payment == payment1.payment;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, payment);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payment=" + payment +
                '}';
    }
}
