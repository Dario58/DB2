package it.polimi.db2.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "service", schema = "db2_project")
@NamedQueries({
        @NamedQuery(name = "ServiceEntity.retrieveAllServices", query = "SELECT s FROM ServiceEntity s"),
})

public class ServiceEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "offer")
    private String offer;

    @Basic
    @Column(name = "mpMins")
    private Integer mpMins;

    @Basic
    @Column(name = "mpSms")
    private Integer mpSms;

    @Basic
    @Column(name = "mpExtraMinsCost")
    private Integer mpExtraMinsCost;

    @Basic
    @Column(name = "mpExtraSmsCost")
    private Integer mpExtraSmsCost;

    @Basic
    @Column(name = "fiGBs")
    private Integer fiGBs;

    @Basic
    @Column(name = "fiExtraGBsCost")
    private Integer fiExtraGBsCost;

    @Basic
    @Column(name = "miGBs")
    private Integer miGBs;

    @Basic
    @Column(name = "miExtraGBsCost")
    private Integer miExtraGBsCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }


    public Integer getMpMins() {
        return mpMins;
    }

    public void setMpMins(Integer mpMins) {
        this.mpMins = mpMins;
    }


    public Integer getMpSms() {
        return mpSms;
    }

    public void setMpSms(Integer mpSms) {
        this.mpSms = mpSms;
    }


    public Integer getMpExtraMinsCost() {
        return mpExtraMinsCost;
    }

    public void setMpExtraMinsCost(Integer mpExtraMinsCost) {
        this.mpExtraMinsCost = mpExtraMinsCost;
    }


    public Integer getMpExtraSmsCost() {
        return mpExtraSmsCost;
    }

    public void setMpExtraSmsCost(Integer mpExtraSmsCost) {
        this.mpExtraSmsCost = mpExtraSmsCost;
    }


    public Integer getFiGBs() {
        return fiGBs;
    }

    public void setFiGBs(Integer fiGBs) {
        this.fiGBs = fiGBs;
    }


    public Integer getFiExtraGBsCost() {
        return fiExtraGBsCost;
    }

    public void setFiExtraGBsCost(Integer fiExtraGBsCost) {
        this.fiExtraGBsCost = fiExtraGBsCost;
    }


    public Integer getMiGBs() {
        return miGBs;
    }

    public void setMiGBs(Integer miGBs) {
        this.miGBs = miGBs;
    }


    public Integer getMiExtraGBsCost() {
        return miExtraGBsCost;
    }

    public void setMiExtraGBsCost(Integer miExtraGBsCost) {
        this.miExtraGBsCost = miExtraGBsCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceEntity that = (ServiceEntity) o;

        if (id != that.id) return false;
        if (offer != null ? !offer.equals(that.offer) : that.offer != null) return false;
        if (mpMins != null ? !mpMins.equals(that.mpMins) : that.mpMins != null) return false;
        if (mpSms != null ? !mpSms.equals(that.mpSms) : that.mpSms != null) return false;
        if (mpExtraMinsCost != null ? !mpExtraMinsCost.equals(that.mpExtraMinsCost) : that.mpExtraMinsCost != null)
            return false;
        if (mpExtraSmsCost != null ? !mpExtraSmsCost.equals(that.mpExtraSmsCost) : that.mpExtraSmsCost != null)
            return false;
        if (fiGBs != null ? !fiGBs.equals(that.fiGBs) : that.fiGBs != null) return false;
        if (fiExtraGBsCost != null ? !fiExtraGBsCost.equals(that.fiExtraGBsCost) : that.fiExtraGBsCost != null)
            return false;
        if (miGBs != null ? !miGBs.equals(that.miGBs) : that.miGBs != null) return false;
        if (miExtraGBsCost != null ? !miExtraGBsCost.equals(that.miExtraGBsCost) : that.miExtraGBsCost != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (offer != null ? offer.hashCode() : 0);
        result = 31 * result + (mpMins != null ? mpMins.hashCode() : 0);
        result = 31 * result + (mpSms != null ? mpSms.hashCode() : 0);
        result = 31 * result + (mpExtraMinsCost != null ? mpExtraMinsCost.hashCode() : 0);
        result = 31 * result + (mpExtraSmsCost != null ? mpExtraSmsCost.hashCode() : 0);
        result = 31 * result + (fiGBs != null ? fiGBs.hashCode() : 0);
        result = 31 * result + (fiExtraGBsCost != null ? fiExtraGBsCost.hashCode() : 0);
        result = 31 * result + (miGBs != null ? miGBs.hashCode() : 0);
        result = 31 * result + (miExtraGBsCost != null ? miExtraGBsCost.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceEntity{" +
                "id=" + id +
                '}';
    }
}
