package it.polimi.db2.utils;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;

import java.sql.Date;
import java.util.List;

/**
 * Box object that contains selected parameters
 * for an order
 */
public class FilledOrder {
    private final BundleEntity bundle;
    private final ValidityPeriodEntity validityPeriod;
    private final List<OptionalProductEntity> chosenOptionals;
    private final Date startDate;
    private final int totalExpenditure;

    public FilledOrder(BundleEntity bundle, ValidityPeriodEntity validityPeriod, List<OptionalProductEntity> chosenOptionals, Date startDate) {
        this.bundle = bundle;
        this.validityPeriod = validityPeriod;
        this.chosenOptionals = chosenOptionals;
        this.startDate = startDate;

        int total = validityPeriod.getMonths() * validityPeriod.getCostPerMonth();
        if(chosenOptionals != null) {
            for(OptionalProductEntity op : chosenOptionals) {
                total += validityPeriod.getMonths() * op.getMonthlyFee();
            }
        }
        this.totalExpenditure = total;
    }

    public BundleEntity getBundle() {
        return bundle;
    }

    public ValidityPeriodEntity getValidityPeriod() {
        return validityPeriod;
    }

    public List<OptionalProductEntity> getChosenOptionals() {
        return chosenOptionals;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getTotalExpenditure() {
        return totalExpenditure;
    }

    @Override
    public String toString() {
        return "FilledOrder{" +
                "bundle=" + bundle +
                ", validityPeriod=" + validityPeriod +
                ", chosenOptionals=" + chosenOptionals +
                ", startDate=" + startDate +
                ", totalExpenditure=" + totalExpenditure +
                '}';
    }
}
