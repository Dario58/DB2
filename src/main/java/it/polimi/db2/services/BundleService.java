package it.polimi.db2.services;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.BundleExistentException;

import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class BundleService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public BundleEntity findBundleByBundleId(int bundleId) {
        return em.find(BundleEntity.class, bundleId);
    }

    public BundleEntity findBundleByTitle(String title) {
        return em.createNamedQuery("BundleEntity.findBundleByTitle", BundleEntity.class)
                .setParameter("title", title)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<BundleEntity> retrieveAllBundles() {
        return em.createNamedQuery("BundleEntity.retrieveAll", BundleEntity.class)
                .getResultList();
    }

    public BundleEntity addNewBundle(String title, List<Integer> listOfServices, List<Integer> listOfValidityPeriods) throws BundleExistentException {

        System.out.println("Creating new user with title: " + title + ", services ids:" + listOfServices + ", validityPeriods ids:" + listOfValidityPeriods);

        if (findBundleByTitle(title) != null) {
            throw new BundleExistentException("Bundle title already in use!");
        }

        BundleEntity newBundle = new BundleEntity(title);
        em.persist(newBundle);

        int bundleId = findBundleByTitle(title).getId();
        addServicesToBundle(listOfServices, bundleId);
        addValidityPeriodsToBundle(listOfValidityPeriods, bundleId);

        System.out.println("Created bundle OK: " + title);

        return newBundle;
    }

    public List<ValidityPeriodEntity> findValidityPeriodsByBundleId(int bId) {

    }

    public List<ServiceEntity> findServicesByBundleId(int bId) {

    }

    private void addServicesToBundle(List<Integer> listOfServices, int bundleId) {
        String query;
        for(int service : listOfServices) {
            query = "INSERT INTO servicesinbundle (serviceId, bundleId) VALUES (" + service + "," + bundleId + ")";
            em.createQuery(query);
        }
    }

    private void addValidityPeriodsToBundle(List<Integer> listOfValidityPeriods, int bundleId) {
        String query;
        for(int validityPeriod : listOfValidityPeriods) {
            query = "INSERT INTO validityperiodsperbundle (serviceId, bundleId) VALUES (" + bundleId + "," + validityPeriod + ")";
            em.createQuery(query);
        }
    }
}
