package it.polimi.db2.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "order", schema = "db2_project")
public class OrderEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "issueTime")
    private Timestamp issueTime;
    @Basic
    @Column(name = "totCost")
    private int totCost;
    @Basic
    @Column(name = "valid")
    private Boolean valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime = issueTime;
    }


    public int getTotCost() {
        return totCost;
    }

    public void setTotCost(int totCost) {
        this.totCost = totCost;
    }


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (totCost != that.totCost) return false;
        if (issueTime != null ? !issueTime.equals(that.issueTime) : that.issueTime != null) return false;
        if (valid != null ? !valid.equals(that.valid) : that.valid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (issueTime != null ? issueTime.hashCode() : 0);
        result = 31 * result + totCost;
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }
}
