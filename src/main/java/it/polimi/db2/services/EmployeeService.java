package it.polimi.db2.services;


import it.polimi.db2.entities.EmployeeEntity;
import it.polimi.db2.exceptions.CredentialException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;


    public EmployeeEntity checkCredentials(String nickname, String password) throws NonUniqueResultException, CredentialException {
        List<EmployeeEntity> uList;

        try {
            uList = em.createNamedQuery("EmployeeEntity.checkCredentials", EmployeeEntity.class)
                    .setParameter("nickname", nickname)
                    .setParameter("password", password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new CredentialException("Could not verify credentals");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }
}