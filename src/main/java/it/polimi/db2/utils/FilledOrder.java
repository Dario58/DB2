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
    private final UserEntity user;
    private final BundleEntity bundle;
    private final ValidityPeriodEntity validityPeriod;
    private final List<OptionalProductEntity> availableOptionals;
    private final Date startDate;

    public FilledOrder(UserEntity user, BundleEntity bundle, ValidityPeriodEntity validityPeriod, List<OptionalProductEntity> availableOptionals, Date startDate) {
        this.user = user;
        this.bundle = bundle;
        this.validityPeriod = validityPeriod;
        this.availableOptionals = availableOptionals;
        this.startDate = startDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public BundleEntity getBundle() {
        return bundle;
    }

    public ValidityPeriodEntity getValidityPeriod() {
        return validityPeriod;
    }

    public List<OptionalProductEntity> getAvailableOptionals() {
        return availableOptionals;
    }

    public Date getStartDate() {
        return startDate;
    }
}
