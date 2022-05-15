package it.polimi.db2.utils;


import it.polimi.db2.entities.AlertEntity;
import it.polimi.db2.entities.OrderEntity;
import it.polimi.db2.entities.UserEntity;

import java.util.HashMap;
import java.util.List;


public class SalesReport {
    List<AverageNumberOptionalsPackage> averageNumberOptionalPerPackage;
    List<BestSellerOptional> bestSellerOptional;
    List<TotValuePerPackageSold> totValuePerPackageSold;
    List<PurchasesPerPackage> purchasesPerPackage;
    List<PurchasesPerPackageValidity> purchasesPerPackageValidity;
    List<AlertEntity> alerts;
    List<UserEntity> insolvents;
    List<OrderEntity> failedOrders;

    public SalesReport(List<AverageNumberOptionalsPackage> averageNumberOptionalPerPackage, List<BestSellerOptional> bestSellerOptional, List<TotValuePerPackageSold> totValuePerPackageSold, List<PurchasesPerPackage> purchasesPerPackage, List<PurchasesPerPackageValidity> purchasesPerPackageValidity, List<AlertEntity> alerts, List<UserEntity> insolvents, List<OrderEntity> failedOrders) {
        this.averageNumberOptionalPerPackage = averageNumberOptionalPerPackage;
        this.bestSellerOptional = bestSellerOptional;
        this.totValuePerPackageSold = totValuePerPackageSold;
        this.purchasesPerPackage = purchasesPerPackage;
        this.purchasesPerPackageValidity = purchasesPerPackageValidity;
        this.alerts = alerts;
        this.insolvents = insolvents;
        this.failedOrders = failedOrders;
    }

    public SalesReport() {

    }

    public List<AverageNumberOptionalsPackage> getAverageNumberOptionalPerPackage() {
        return averageNumberOptionalPerPackage;
    }

    public void setAverageNumberOptionalPerPackage(List<AverageNumberOptionalsPackage> averageNumberOptionalPerPackage) {
        this.averageNumberOptionalPerPackage = averageNumberOptionalPerPackage;
    }

    public List<BestSellerOptional> getBestSellerOptional() {
        return bestSellerOptional;
    }

    public void setBestSellerOptional(List<BestSellerOptional> bestSellerOptional) {
        this.bestSellerOptional = bestSellerOptional;
    }

    public List<TotValuePerPackageSold> getTotValuePerPackageSold() {
        return totValuePerPackageSold;
    }

    public void setTotValuePerPackageSold(List<TotValuePerPackageSold> totValuePerPackageSold) {
        this.totValuePerPackageSold = totValuePerPackageSold;
    }

    public List<PurchasesPerPackage> getPurchasesPerPackage() {
        return purchasesPerPackage;
    }

    public void setPurchasesPerPackage(List<PurchasesPerPackage> purchasesPerPackage) {
        this.purchasesPerPackage = purchasesPerPackage;
    }

    public List<PurchasesPerPackageValidity> getPurchasesPerPackageValidity() {
        return purchasesPerPackageValidity;
    }

    public void setPurchasesPerPackageValidity(List<PurchasesPerPackageValidity> purchasesPerPackageValidity) {
        this.purchasesPerPackageValidity = purchasesPerPackageValidity;
    }

    public List<AlertEntity> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertEntity> alerts) {
        this.alerts = alerts;
    }

    public List<UserEntity> getInsolvents() {
        return insolvents;
    }

    public void setInsolvents(List<UserEntity> insolvents) {
        this.insolvents = insolvents;
    }

    public List<OrderEntity> getFailedOrders() {
        return failedOrders;
    }

    public void setFailedOrders(List<OrderEntity> failedOrders) {
        this.failedOrders = failedOrders;
    }

    @Override
    public String toString() {
        return "SalesReport{" +
                "averageNumberOptionalPerPackage=" + averageNumberOptionalPerPackage +
                ", bestSellerOptional=" + bestSellerOptional +
                ", totValuePerPackageSold=" + totValuePerPackageSold +
                ", purchasesPerPackage=" + purchasesPerPackage +
                ", purchasesPerPackageValidity=" + purchasesPerPackageValidity +
                ", alerts=" + alerts +
                ", insolvents=" + insolvents +
                ", failedOrders=" + failedOrders +
                '}';
    }
}
