package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "optionalproduct", schema = "db2_project")
@NamedQueries({
        @NamedQuery(name = "OptionalProductEntity.retrieveAllOptional", query = "SELECT s FROM OptionalProductEntity s"),
        @NamedQuery(name = "OptionalProductEntity.checkValidity", query = "SELECT s FROM OptionalProductEntity s WHERE s.title = :title")
})
public class OptionalProductEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "monthlyFee")
    private int monthlyFee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionalProductEntity that = (OptionalProductEntity) o;

        if (id != that.id) return false;
        if (monthlyFee != that.monthlyFee) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + monthlyFee;
        return result;
    }

    @Override
    public String toString() {
        return "OptionalProductEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", monthlyFee=" + monthlyFee +
                '}';
    }
}
