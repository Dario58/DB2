package it.polimi.db2.entities;



import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "alert", schema = "db2_project")
public class AlertEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "userId"),
            @JoinColumn(name = "nickname")
    })
    private UserEntity user;

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

    public UserEntity getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
        return id == that.id && amountLastRejection == that.amountLastRejection && user.equals(that.user) && dateLastRejection.equals(that.dateLastRejection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, dateLastRejection, amountLastRejection);
    }
}
