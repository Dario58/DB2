package it.polimi.db2.services;

import it.polimi.db2.entities.BundleEntity;
import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class PackageService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;


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
    public List<ServiceEntity> retrieveAllService() {
        return em.createNamedQuery("ServiceEntity.retrieveAllServices", ServiceEntity.class)
                .getResultList();
    }



}
