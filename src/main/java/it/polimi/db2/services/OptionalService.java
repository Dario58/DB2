package it.polimi.db2.services;

import it.polimi.db2.entities.OptionalProductEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.exceptions.CredentialException;

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

    public List<OptionalProductEntity> retrieveProductByTitle(){
        return em.createNamedQuery("OptionalProductEntity.retrieveAllOptional", OptionalProductEntity.class)
                .getResultList();
    }

    public OptionalProductEntity checkValidity(String title) throws CredentialException {

        List<OptionalProductEntity> list;

        try{
            list = em.createNamedQuery("OptionalProductEntity.checkValidity", OptionalProductEntity.class)
                    .setParameter("title", title)
                    .getResultList();
        } catch (PersistenceException e) {

            throw new CredentialException("Could not verify credentials");
        }
        if(list.isEmpty()){
            return null;
        }
        throw new NonUniqueResultException("Invalid title. An optional product with this title already exists.");

    }

}
