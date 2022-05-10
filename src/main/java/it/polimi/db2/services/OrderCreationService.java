package it.polimi.db2.services;

import it.polimi.db2.entities.OrderEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Stateless
public class OrderCreationService {

    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    
}
