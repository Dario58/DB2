package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "validityperiod", schema = "db2_project")
@NamedQueries({
        @NamedQuery(name = "ValidityPeriodEntity.retrieveAllPeriods", query = "SELECT s FROM ValidityPeriodEntity s"),
        @NamedQuery(name = "ValidityPeriodEntity.checkValidity", query = "SELECT s FROM ValidityPeriodEntity s WHERE (s.months = :months AND s.costPerMonth = :costPerMonth)")
})

public class ValidityPeriodEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "months")
    private int months;

    @Basic
    @Column(name = "costPerMonth")
    private int costPerMonth;

    public ValidityPeriodEntity(int months, int costPerMonth) {
        this.months = months;
        this.costPerMonth = costPerMonth;
    }

    public ValidityPeriodEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }


    public int getCostPerMonth() {
        return costPerMonth;
    }

    public void setCostPerMonth(int costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidityPeriodEntity that = (ValidityPeriodEntity) o;

        if (id != that.id) return false;
        if (months != that.months) return false;
        if (costPerMonth != that.costPerMonth) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + months;
        result = 31 * result + costPerMonth;
        return result;
    }

    @Override
    public String toString() {
        return "ValidityPeriodEntity{" +
                "id=" + id +
                ", months=" + months +
                ", costPerMonth=" + costPerMonth +
                '}';
    }
}
