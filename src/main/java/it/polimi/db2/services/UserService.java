package it.polimi.db2.services;

import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.exception.CredentialException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public UserEntity findUserByUserId(int userId) {
        return em.find(UserEntity.class, userId);
    }

    public UserEntity findUserByUsername(String username) {
        return em.createNamedQuery("UserEntity.findByNickname", UserEntity.class)
                .setParameter("nickname", username)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public UserEntity findUserByEmail(String email) {
        return em.createNamedQuery("UserEntity.findByEmail", UserEntity.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public UserEntity checkCredentials(String username, String password) throws CredentialException, NonUniqueResultException {
        List<UserEntity> uList;

        try {
            System.out.println("PRIMAAAAAAAAAAAAAA");
            uList = em.createNamedQuery("UserEntity.checkCredentials", UserEntity.class)
                    .setParameter("nickname", username)
                    .setParameter("password", password)
                    .getResultList();
            System.out.println("AOOOOOOOOOOOOOOOOO");
        } catch (PersistenceException e) {
            throw new CredentialException("Could not verify credentials");
        }

        if (uList.isEmpty()) {
            return null;
        } else if (uList.size() == 1) {
            return uList.get(0);
        }

        throw new NonUniqueResultException("More than one user registered with same credentials.");
    }

    public UserEntity addNewUser(String username, String password, String email) throws CredentialException {
        if (findUserByUsername(username) != null) {
            throw new CredentialException("Username already in use!");
        }

        if (findUserByEmail(email) != null) {
            throw new CredentialException("Email already in use!");
        }

        UserEntity newUser = new UserEntity(username, password, email);
        em.persist(newUser);

        return newUser;
    }

    public void setUserInsolvent(int userId) {
        UserEntity user = em.find(UserEntity.class, userId);
        user.setInsolvent(true);

        em.merge(user);
    }

    public void setUserFlagged(int userId) {
        UserEntity user = em.find(UserEntity.class, userId);
        user.setFlag(true);

        em.merge(user);
    }
}
