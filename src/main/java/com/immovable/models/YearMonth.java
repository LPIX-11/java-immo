package main.java.com.immovable.models;

import javax.persistence.*;

@Entity
public class YearMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Month month;

    @ManyToOne
    private Year year;

    public YearMonth() {
    }

    public YearMonth(Month month, Year year) {
        this.month = month;
        this.year = year;
    }

    public YearMonth(Paiement paiement, YearMonth yearMonthById) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
