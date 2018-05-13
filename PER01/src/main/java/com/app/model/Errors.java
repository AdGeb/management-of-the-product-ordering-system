package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Errors {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime date;
    private String message;

    public Errors(LocalDateTime date, String message) {
        this.date = date;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Errors errors = (Errors) o;
        return Objects.equals(id, errors.id) &&
                Objects.equals(date, errors.date) &&
                Objects.equals(message, errors.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, date, message);
    }

    @Override
    public String toString() {
        return "Errors{" +
                "id=" + id +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
