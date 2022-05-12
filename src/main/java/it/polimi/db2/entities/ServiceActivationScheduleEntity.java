package it.polimi.db2.entities;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "serviceactivationschedule", schema = "db2_project")
public class ServiceActivationScheduleEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "activationDate")
    private Date activationDate;

    @Basic
    @Column(name = "deactivationDate")
    private Date deactivationDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @OneToOne(optional = false)
    @JoinColumn(name = "orderId")
    private OrderEntity order;

    public ServiceActivationScheduleEntity(OrderEntity order) {
        this.order = order;
        this.activationDate = order.getStartDate();
        this.user = order.getUser();

        LocalDate localDate = activationDate.toLocalDate();
        this.deactivationDate = java.sql.Date.valueOf(localDate.plusMonths(order.getValidityPeriodInOrder().getMonths()));
    }

    public ServiceActivationScheduleEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }


    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceActivationScheduleEntity that = (ServiceActivationScheduleEntity) o;
        return id == that.id && activationDate.equals(that.activationDate) && deactivationDate.equals(that.deactivationDate) && user.equals(that.user) && order.equals(that.order);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (activationDate != null ? activationDate.hashCode() : 0);
        result = 31 * result + (deactivationDate != null ? deactivationDate.hashCode() : 0);
        return result;
    }
}
