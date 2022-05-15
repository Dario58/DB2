package it.polimi.db2.services;

import it.polimi.db2.entities.*;
import it.polimi.db2.exceptions.BundleExistentException;
import it.polimi.db2.utils.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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

    public List<ValidityPeriodEntity> findValidityPeriodsByBundleId(int bId) {
        List<ValidityPeriodEntity> vPeriods = new ArrayList<>();
        List<Integer> validityPeriodsIds = em.createNamedQuery("BundleEntity.findValidityPeriodsById")
                .setParameter("1", bId)
                .getResultList();

        for(int id : validityPeriodsIds) vPeriods.add(em.find(ValidityPeriodEntity.class, id));

        return vPeriods;
    }

    public List<ServiceEntity> findServicesByBundleId(int bId) {
        List<ServiceEntity> services = new ArrayList<>();
        List<Integer> servicesIds = em.createNamedQuery("BundleEntity.findServicesById")
                .setParameter("1", bId)
                .getResultList();

        for(int id : servicesIds) services.add(em.find(ServiceEntity.class, id));

        return services;
    }

    public List<OptionalProductEntity> findAvailableOptionalsByBundleId(int bId) {
        List<OptionalProductEntity> optionals = new ArrayList<>();
        List<Integer> availableOptionalsIds = em.createNamedQuery("BundleEntity.findAvailableOptionalsById")
                .setParameter("1", bId)
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

    public List<OrderEntity> suspendedOrders (){
        return em.createNamedQuery("OrderEntity.getSuspended").getResultList();
    }

    public List<OrderEntity> suspendedOrderByUser (int uId) {
        return em.createNamedQuery("OrderEntity.getFailedOrdersUser").setParameter("uId", uId).getResultList();
    }
}
