package it.polimi.db2.entities;

import javax.persistence.*;

@Entity
@Table(name = "service", schema = "db2_project")
public class ServiceEntity {
    private int id;
    private String offer;
    private Integer mpMins;
    private Integer mpSms;
    private Integer mpExtraMinsCost;
    private Integer mpExtraSmsCost;
    private Integer fiGBs;
    private Integer fiExtraGBsCost;
    private Integer miGBs;
    private Integer miExtraGBsCost;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "offer")
    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    @Basic
    @Column(name = "mpMins")
    public Integer getMpMins() {
        return mpMins;
    }

    public void setMpMins(Integer mpMins) {
        this.mpMins = mpMins;
    }

    @Basic
    @Column(name = "mpSms")
    public Integer getMpSms() {
        return mpSms;
    }

    public void setMpSms(Integer mpSms) {
        this.mpSms = mpSms;
    }

    @Basic
    @Column(name = "mpExtraMinsCost")
    public Integer getMpExtraMinsCost() {
        return mpExtraMinsCost;
    }

    public void setMpExtraMinsCost(Integer mpExtraMinsCost) {
        this.mpExtraMinsCost = mpExtraMinsCost;
    }

    @Basic
    @Column(name = "mpExtraSmsCost")
    public Integer getMpExtraSmsCost() {
        return mpExtraSmsCost;
    }

    public void setMpExtraSmsCost(Integer mpExtraSmsCost) {
        this.mpExtraSmsCost = mpExtraSmsCost;
    }

    @Basic
    @Column(name = "fiGBs")
    public Integer getFiGBs() {
        return fiGBs;
    }

    public void setFiGBs(Integer fiGBs) {
        this.fiGBs = fiGBs;
    }

    @Basic
    @Column(name = "fiExtraGBsCost")
    public Integer getFiExtraGBsCost() {
        return fiExtraGBsCost;
    }

    public void setFiExtraGBsCost(Integer fiExtraGBsCost) {
        this.fiExtraGBsCost = fiExtraGBsCost;
    }

    @Basic
    @Column(name = "miGBs")
    public Integer getMiGBs() {
        return miGBs;
    }

    public void setMiGBs(Integer miGBs) {
        this.miGBs = miGBs;
    }

    @Basic
    @Column(name = "miExtraGBsCost")
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
}
