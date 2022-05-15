package it.polimi.db2.utils;


import javax.persistence.*;

@Entity
@Table(name = "purchasesperpackage", schema = "db2_project")
@NamedQuery(name = "PPP", query = "SELECT new it.polimi.db2.utils.PurchasesPerPackage(b.title, p.purchaseCount) FROM PurchasesPerPackage p JOIN BundleEntity b ON p.bundleId = b.id")
public class PurchasesPerPackage {

    @Id
    @Column(name = "bundleId")
    private int bundleId;

    private String title;

    @Basic
    @Column(name = "purchaseCount")
    int purchaseCount;

    public PurchasesPerPackage(String title, int purchaseCount) {
        this.title = title;
        this.purchaseCount = purchaseCount;
    }

    public PurchasesPerPackage() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    @Override
    public String toString() {
        return "PurchasesPerPackage{" +
                "title='" + title + '\'' +
                ", purchaseCount=" + purchaseCount +
                '}';
    }
}
