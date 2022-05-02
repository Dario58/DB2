package it.polimi.db2.utils;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.OptionalProductEntity;
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
    private final List<OptionalProductEntity> availableOptionals;

    public Product(BundleEntity bundle, List<ValidityPeriodEntity> validityPeriods, List<ServiceEntity> services, List<OptionalProductEntity> availableOptionals) {
        this.bundle = bundle;
        this.validityPeriods = validityPeriods;
        this.services = services;
        this.availableOptionals = availableOptionals;
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

    public List<OptionalProductEntity> getAvailableOptionals() {
        return availableOptionals;
    }
}
