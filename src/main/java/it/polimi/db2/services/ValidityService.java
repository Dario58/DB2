package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.ValidityPeriodEntity;
import it.polimi.db2.exceptions.OptionalExistentException;
import it.polimi.db2.exceptions.ValidityPeriodExistentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class ValidityService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public List<ValidityPeriodEntity> retrieveAllValidityPeriods(){
        return em.createNamedQuery("ValidityPeriodEntity.retrieveAllPeriods", ValidityPeriodEntity.class)
                .getResultList();
    }

    public void createValidityPeriod(ValidityPeriodEntity validityPeriodEntity){
        em.persist(validityPeriodEntity);
    }

    public void checkValidity(int months, int monthlyFee) throws ValidityPeriodExistentException {

        List<ValidityPeriodEntity> list;

        try{
            list = em.createNamedQuery("ValidityPeriodEntity.checkValidity", ValidityPeriodEntity.class)
                    .setParameter("months", months)
                    .setParameter("costPerMonth", monthlyFee)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not verify existence");
        }

        if(list.isEmpty()){
            return;
        }

        throw new ValidityPeriodExistentException("Validity period already exists.");
    }
}
