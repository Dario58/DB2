package it.polimi.db2.services;

import it.polimi.db2.entities.OrderEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderCreationService {

    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public void createOrder() {

    }
}
