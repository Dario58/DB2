package it.polimi.db2.services;

import it.polimi.db2.entities.AlertEntity;
import it.polimi.db2.entities.OrderEntity;
import it.polimi.db2.entities.UserEntity;
import it.polimi.db2.utils.*;
import it.polimi.db2.utils.PurchasesPerPackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.List;

@Stateless
public class SalesReportService {
    @PersistenceContext(unitName = "DB2")
    private EntityManager em;

    public SalesReport createSalesReport() {
        //Query returns null if empty
        List<AverageNumberOptionalsPackage> anop = em.createNamedQuery("ANOP").getResultList();
        List<BestSellerOptional> bso = em.createNamedQuery("BSO").getResultList();
        List<PurchasesPerPackage> ppp = em.createNamedQuery("PPP").getResultList();
        List<PurchasesPerPackageValidity> pppv = em.createNamedQuery("PPPV").getResultList();
        List<TotValuePerPackageSold> tvpps = em.createNamedQuery("TVPPS").getResultList();
        List<AlertEntity> alerts = em.createNamedQuery("AlertEntity.retrieveAll").getResultList();
        List<UserEntity> insolventUsers = em.createNamedQuery("UserEntity.getInsolvents").getResultList();
        List<OrderEntity> suspendedOrders = em.createNamedQuery("OrderEntity.getSuspended").getResultList();

        return new SalesReport(anop, bso, tvpps, ppp, pppv, alerts, insolventUsers, suspendedOrders);
    }
}
