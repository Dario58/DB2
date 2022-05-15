package it.polimi.db2.services;

import it.polimi.db2.entities.OrderEntity;
import it.polimi.db2.entities.ServiceActivationScheduleEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.utils.FilledOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderCreationService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public OrderEntity findOrderById(int oId) {
        em.clear();
        return em.find(OrderEntity.class, oId);
    }

    public OrderEntity createOrder(FilledOrder filledOrder, UserEntity currentUser) {
        OrderEntity order = new OrderEntity(filledOrder.getTotalExpenditure(), '?', filledOrder.getStartDate(), filledOrder.getChosenOptionals(), filledOrder.getValidityPeriod(), filledOrder.getBundle(), currentUser);
        em.persist(order);
        return order;
    }

    public void updateOrderOk(int oId) {
        OrderEntity order = findOrderById(oId);
        order.setValid('y');
        em.merge(order);

        em.flush();
    }

    public void updateOrderKo(int oId) {
        OrderEntity order = findOrderById(oId);
        order.setValid('n');
        em.merge(order);

        em.flush();
    }

    public void createActivationSchedule(int oId) {
        OrderEntity order = em.find(OrderEntity.class, oId);
        ServiceActivationScheduleEntity serviceActivationSchedule = new ServiceActivationScheduleEntity(order);
        em.persist(serviceActivationSchedule);
    }
}
