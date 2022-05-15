package it.polimi.db2.utils;


import javax.persistence.*;

@Entity
@Table(name = "bestselleroptional", schema = "db2_project")
@NamedQuery(name = "BSO", query = "SELECT new it.polimi.db2.utils.BestSellerOptional(op.title, bso.totRevenue) FROM BestSellerOptional bso JOIN OptionalProductEntity op ON bso.optionalId = op.id ORDER BY bso.totRevenue DESC")
public class BestSellerOptional {

    @Id
    @Column(name = "optionalId")
    private int optionalId;

    private String title;

    @Basic
    @Column(name = "totRevenue")
    int totRevenue;

    public BestSellerOptional(String title, int totRevenue) {
        this.title = title;
        this.totRevenue = totRevenue;
    }

    public BestSellerOptional() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotRevenue() {
        return totRevenue;
    }

    public void setTotRevenue(int totRevenue) {
        this.totRevenue = totRevenue;
    }

    @Override
    public String toString() {
        return "BestSellerOptional{" +
                "title='" + title + '\'' +
                ", totRevenue=" + totRevenue +
                '}';
    }
}
