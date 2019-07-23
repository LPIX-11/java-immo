package main.java.com.immovable.models;

import javax.persistence.*;

@Entity
public class PaymentMonth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private YearMonth yearMonth;

    @ManyToOne
    private Paiement paiement;

    public PaymentMonth(YearMonth yearMonth, Paiement paiement) {
        this.yearMonth = yearMonth;
        this.paiement = paiement;
    }

    public PaymentMonth() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}
