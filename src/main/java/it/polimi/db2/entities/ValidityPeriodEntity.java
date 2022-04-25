package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "validityperiod", schema = "db2_project")
public class ValidityPeriodEntity {
    private int id;
    private int months;
    private int costPerMonth;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "months")
    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    @Basic
    @Column(name = "costPerMonth")
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
}
