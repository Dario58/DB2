package it.polimi.db2.utils;


import javax.persistence.*;

@Entity
@Table(name = "purchaseperpackagevalidityperiod", schema = "db2_project")
@NamedQuery(name = "PPPV", query = "SELECT new it.polimi.db2.utils.PurchasesPerPackageValidity(b.title, p.purchaseCount, p.validityId) FROM PurchasesPerPackageValidity p JOIN BundleEntity b ON p.bundleId = b.id")
public class PurchasesPerPackageValidity {

    @Id
    @Column(name = "bundleId")
    private int bundleId;

    private String title;

    @Basic
    @Column(name = "purchaseCount")
    int purchaseCount;

    @Basic
    @Column(name = "validityId")
    int validityId;

    public PurchasesPerPackageValidity(String title, int purchaseCount, int validityId) {
        this.title = title;
        this.purchaseCount = purchaseCount;
        this.validityId = validityId;
    }

    public PurchasesPerPackageValidity() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValidityId() {
        return validityId;
    }

    public void setValidityId(int validityId) {
        this.validityId = validityId;
    }

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    @Override
    public String toString() {
        return "PurchasesPerPackageValidity{" +
                "title='" + title + '\'' +
                ", purchaseCount=" + purchaseCount +
                ", validityId=" + validityId +
                '}';
    }
}
