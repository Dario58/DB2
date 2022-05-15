package it.polimi.db2.utils;


import javax.persistence.*;

@Entity
@Table(name = "totvalueperpackagesold", schema = "db2_project")
@NamedQuery(name = "TVPPS", query = "SELECT new it.polimi.db2.utils.TotValuePerPackageSold(b.title, t.totValue, t.totValueNoOptionals) FROM TotValuePerPackageSold t JOIN BundleEntity b ON t.bundleId = b.id")
public class TotValuePerPackageSold {

    @Id
    @Column(name = "bundleId")
    private int bundleId;

    private String title;

    @Basic
    @Column(name = "totValue")
    int totValue;

    @Basic
    @Column(name = "totValueNoOptionals")
    int totValueNoOptionals;

    public TotValuePerPackageSold(String title, int totValue, int totValueNoOptionals) {
        this.title = title;
        this.totValue = totValue;
        this.totValueNoOptionals = totValueNoOptionals;
    }

    public TotValuePerPackageSold() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotValue() {
        return totValue;
    }

    public void setTotValue(int totValue) {
        this.totValue = totValue;
    }

    public int getTotValueNoOptionals() {
        return totValueNoOptionals;
    }

    public void setTotValueNoOptionals(int totValueNoOptionals) {
        this.totValueNoOptionals = totValueNoOptionals;
    }

    @Override
    public String toString() {
        return "TotValuePerPackageSold{" +
                "title='" + title + '\'' +
                ", totValue=" + totValue +
                ", totValueNoOptionals=" + totValueNoOptionals +
                '}';
    }
}
