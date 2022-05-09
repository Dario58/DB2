package it.polimi.db2.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "alert", schema = "db2_project")
public class AlertEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "dateLastRejection")
    private Date dateLastRejection;
    @Basic
    @Column(name = "amountLastRejection")
    private int amountLastRejection;

    public Date getDateLastRejection() {
        return dateLastRejection;
    }

    public int getAmountLastRejection() {
        return amountLastRejection;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmountLastRejection(int amountLastRejection) {
        this.amountLastRejection = amountLastRejection;
    }

    public void setDateLastRejection(Date dateLastRejection) {
        this.dateLastRejection = dateLastRejection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AlertEntity that = (AlertEntity) o;

        if (id != that.id) return false;
        if (amountLastRejection != that.amountLastRejection) return false;
        if (dateLastRejection != null ? !dateLastRejection.equals(that.dateLastRejection) : that.dateLastRejection != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + amountLastRejection;
        result = 31 * result + (dateLastRejection != null ? dateLastRejection.hashCode() : 0);
        return result;
    }
}
