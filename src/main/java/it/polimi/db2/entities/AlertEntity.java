package it.polimi.db2.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "alert", schema = "db2_project")
@NamedQuery(name = "AlertEntity.retrieveAll", query = "SELECT new it.polimi.db2.entities.AlertEntity(u, a.dateLastRejection, a.amountLastRejection) FROM AlertEntity a JOIN UserEntity u ON u.id = a.userId")
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

    @Column(name = "userId", updatable = false, insertable = false)
    private int userId;

    @Column(name = "nickname", updatable = false, insertable = false)
    private String nickname;

    @Basic
    @Column(name = "dateLastRejection")
    private Date dateLastRejection;

    @Basic
    @Column(name = "amountLastRejection")
    private int amountLastRejection;

    public AlertEntity(UserEntity user, Date dateLastRejection, int amountLastRejection) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.dateLastRejection = dateLastRejection;
        this.amountLastRejection = amountLastRejection;
    }

    public AlertEntity() {

    }

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

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
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
