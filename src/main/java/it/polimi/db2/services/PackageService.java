package it.polimi.db2.services;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.BundleExistentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;


@Stateless
public class PackageService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public void checkValidity(String title, List<ServiceEntity> services, List<ValidityPeriodEntity> periods, List<OptionalProductEntity> optionals) throws BundleExistentException {
        BundleEntity bundleEntity = new BundleEntity(title, services, periods, optionals);
        List<BundleEntity> bundleEntities = null;

        try{
            bundleEntities = em.createNamedQuery("BundleEntity.retrieveAll", BundleEntity.class)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not verify existence");
        }

        if(bundleEntities == null || bundleEntities.isEmpty()) return;

        for(BundleEntity b : bundleEntities) {
            if(b.equals(bundleEntity)) {
                throw new BundleExistentException("Bundle already exists.");
            }
        }

    }

    public void createBundle(BundleEntity bundleEntity){
        em.persist(bundleEntity);
    }

    public List<OptionalProductEntity> retrieveAllOptional(){
        return em.createNamedQuery("OptionalProductEntity.retrieveAllOptional", OptionalProductEntity.class)
                .getResultList();
    }

    public List<ValidityPeriodEntity> retrieveAllPeriods(){
        return em.createNamedQuery("ValidityPeriodEntity.retrieveAllPeriods", ValidityPeriodEntity.class)
                .getResultList();
    }

    public List<BundleEntity> retrieveAllBundles() {
        return em.createNamedQuery("BundleEntity.retrieveAll", BundleEntity.class)
                .getResultList();
    }
    public List<ServiceEntity> retrieveAllServices() {
        return em.createNamedQuery("ServiceEntity.retrieveAllServices", ServiceEntity.class)
                .getResultList();
    }



}
