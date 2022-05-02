package it.polimi.db2.services;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.BundleExistentException;
import it.polimi.db2.utils.Product;

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
        List<ValidityPeriodEntity> vPeriods = new ArrayList<>();
        List<Integer> validityPeriodsIds = em.createNamedQuery("BundleEntity.findValidityPeriodsById")
                .setParameter("bId", bId)
                .getResultList();

        for(int id : validityPeriodsIds) vPeriods.add(em.find(ValidityPeriodEntity.class, id));

        return vPeriods;
    }

    public List<ServiceEntity> findServicesByBundleId(int bId) {
        List<ServiceEntity> services = new ArrayList<>();
        List<Integer> servicesIds = em.createNamedQuery("BundleEntity.findServicesById")
                .setParameter("bId", bId)
                .getResultList();

        for(int id : servicesIds) services.add(em.find(ServiceEntity.class, id));

        return services;
    }

    public List<OptionalProductEntity> findAvailableOptionalsByBundleId(int bId) {
        List<OptionalProductEntity> optionals = new ArrayList<>();
        List<Integer> availableOptionalsIds = em.createNamedQuery("BundleEntity.findAvailableOptionalsById")
                .setParameter("bId", bId)
                .getResultList();

        for(int id : availableOptionalsIds) optionals.add(em.find(OptionalProductEntity.class, id));

        return optionals;
    }

    public Product buildProduct(BundleEntity b, int bId) {
        List<ValidityPeriodEntity> vps = findValidityPeriodsByBundleId(bId);
        List<ServiceEntity> ss = findServicesByBundleId(bId);
        List<OptionalProductEntity> ops = findAvailableOptionalsByBundleId(bId);

        assert !vps.isEmpty();
        assert !ss.isEmpty();

        return new Product(b, vps, ss, ops);
    }

    private void addServicesToBundle(List<Integer> listOfServices, int bundleId) {
        for(int service : listOfServices) {
            em.createNamedQuery("BundleEntity.addServiceToBundle")
                    .setParameter("service", service)
                    .setParameter("bId", bundleId);
        }
    }

    private void addValidityPeriodsToBundle(List<Integer> listOfValidityPeriods, int bundleId) {
        for(int validityPeriod : listOfValidityPeriods) {
            em.createNamedQuery("BundleEntity.addValidityPeriodToBundle")
                    .setParameter("validityPeriod", validityPeriod)
                    .setParameter("bId", bundleId);
        }
    }

    private void addAvailableOptionalsToBundle(List<Integer> listOfAvailableOptionals, int bundleId) {
        for(int optional : listOfAvailableOptionals) {
            em.createNamedQuery("BundleEntity.addAvailableOptionalToBundle")
                    .setParameter("optional", optional)
                    .setParameter("bId", bundleId);
        }
    }
}
