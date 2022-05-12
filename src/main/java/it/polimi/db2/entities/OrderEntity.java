package it.polimi.db2.entities;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

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
    private Byte valid;

    @Basic
    @Column(name = "startDate")
    private Date startDate;

    @ManyToMany
    @JoinTable(name = "chosenoptionalsinorder",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private Collection<OptionalProductEntity> chosenOptionals;

    @ManyToOne
    @JoinColumn(name = "validityPeriodId")
    private ValidityPeriodEntity validityPeriodInOrder;

    @ManyToOne
    @JoinColumn(name = "bundleId")
    private BundleEntity bundleInOrder;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private UserEntity user;

    public OrderEntity(int totalExpenditure, Byte valid, Date startDate, Collection<OptionalProductEntity> chosenOptionals, ValidityPeriodEntity validityPeriodInOrder, BundleEntity bundleInOrder, UserEntity user) {
        this.valid = valid;
        this.startDate = startDate;
        this.issueTime = Timestamp.from(Instant.now());
        this.totCost = totalExpenditure;
        this.chosenOptionals = chosenOptionals;
        this.validityPeriodInOrder = validityPeriodInOrder;
        this.bundleInOrder = bundleInOrder;
        this.user = user;
    }

    public OrderEntity() {}

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

    public Collection<OptionalProductEntity> getChosenOptionals() {
        return chosenOptionals;
    }

    public ValidityPeriodEntity getValidityPeriodInOrder() {
        return validityPeriodInOrder;
    }

    public BundleEntity getBundleInOrder() {
        return bundleInOrder;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setTotCost(int totCost) {
        this.totCost = totCost;
    }


    public Byte getValid() {
        return valid;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public void setChosenOptionals(Collection<OptionalProductEntity> chosenOptionals) {
        this.chosenOptionals = chosenOptionals;
    }

    public void setValidityPeriodInOrder(ValidityPeriodEntity validityPeriodInOrder) {
        this.validityPeriodInOrder = validityPeriodInOrder;
    }

    public void setBundleInOrder(BundleEntity bundleInOrder) {
        this.bundleInOrder = bundleInOrder;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id && totCost == that.totCost && issueTime.equals(that.issueTime) && valid.equals(that.valid) && chosenOptionals.equals(that.chosenOptionals) && validityPeriodInOrder.equals(that.validityPeriodInOrder) && bundleInOrder.equals(that.bundleInOrder) && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
