package it.polimi.db2.services;

import it.polimi.db2.entities.ServiceEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.ServiceExistentException;
import it.polimi.db2.exceptions.ValidityPeriodExistentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ServiceService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public void checkValidity(String offer, int GBs, int ExtraGBsCost) throws ServiceExistentException {

        List<ServiceEntity> list = null;

        try{
            if(offer.equals("FI")) {
                list = em.createNamedQuery("ServiceEntity.findFiByParameters", ServiceEntity.class)
                        .setParameter("gbs", GBs)
                        .setParameter("extra", ExtraGBsCost)
                        .getResultList();
            } else if(offer.equals("MI")) {
                list = em.createNamedQuery("ServiceEntity.findMiByParameters", ServiceEntity.class)
                        .setParameter("gbs", GBs)
                        .setParameter("extra", ExtraGBsCost)
                        .getResultList();
            }
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not verify existence");
        }

        assert list != null;

        if(list.isEmpty()){
            return;
        }

        throw new ServiceExistentException("Service already exists.");
    }

    public void checkValidity(String offer, int mins, int sms, int extraMins, int extraSms) throws ServiceExistentException {

        assert offer.equals("MP");

        List<ServiceEntity> list;

        try{
            list = em.createNamedQuery("ServiceEntity.findMpByParameters", ServiceEntity.class)
                    .setParameter("mins", mins)
                    .setParameter("sms", sms)
                    .setParameter("extraMins", extraMins)
                    .setParameter("extraSms", extraSms)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not verify existence");
        }

        if(list.isEmpty()){
            return;
        }

        throw new ServiceExistentException("Service already exists.");
    }

    public void createService(ServiceEntity serviceEntity){
        em.persist(serviceEntity);
    }

}
