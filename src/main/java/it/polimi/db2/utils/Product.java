package it.polimi.db2.utils;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;

import java.util.List;

/**
 * Box object that contains bundle with each possible
 * optional and validity period
 */
public class Product {
    private final BundleEntity bundle;
    private final List<ValidityPeriodEntity> validityPeriods;
    private final List<ServiceEntity> services;

    public Product(BundleEntity bundle, List<ValidityPeriodEntity> validityPeriods, List<ServiceEntity> services) {
        this.bundle = bundle;
        this.validityPeriods = validityPeriods;
        this.services = services;
    }

    public BundleEntity getBundle() {
        return bundle;
    }

    public List<ValidityPeriodEntity> getValidityPeriods() {
        return validityPeriods;
    }

    public List<ServiceEntity> getServices() {
        return services;
    }
}
