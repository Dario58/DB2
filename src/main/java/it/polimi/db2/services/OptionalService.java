package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.exceptions.CredentialException;
import it.polimi.db2.exceptions.OptionalExistentException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class OptionalService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public List<OptionalProductEntity> retrieveAllOptionals(){
        return em.createNamedQuery("OptionalProductEntity.retrieveAllOptional", OptionalProductEntity.class)
                .getResultList();
    }

    public void createOptional(OptionalProductEntity optionalProductEntity){
        em.persist(optionalProductEntity);
    }

    public void checkValidity(String title) throws OptionalExistentException {

        List<OptionalProductEntity> list;

        try{
            list = em.createNamedQuery("OptionalProductEntity.checkValidity", OptionalProductEntity.class)
                    .setParameter("title", title)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new PersistenceException("Could not verify existence");
        }

        if(list.isEmpty()){
            return;
        }

        throw new OptionalExistentException("Invalid title. An optional product with this title already exists.");
    }

}
