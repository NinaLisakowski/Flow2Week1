package tester;

import entities.Customer;
import entities.ItemType;
import facades.CustomerFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * @author Nina
 */
public class Tester {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");
    private static final CustomerFacade FACADE = CustomerFacade.getCustomerFacade(EMF);

    public static void main(String[] args) {
       // Persistence.generateSchema("pu", null);

        EntityManager em = EMF.createEntityManager();

        Customer c1 = FACADE.createCustomer("Andreas Knud", "a@k.net");
        Customer c2 = FACADE.createCustomer("Gustav Henriksen", "g@h.net");
        ItemType iT1 = FACADE.createItemType("Pineapple", "Yellow", 35);
        ItemType iT2 = FACADE.createItemType("Apple", "Fruity", 12);

        Customer fc1 = FACADE.findCustomer(2);
        ItemType fIT1 = FACADE.findItemType(2);

        List<Customer> all = FACADE.getAllCustomers();

        try {
            em.getTransaction().begin();

            em.persist(c1);
            em.persist(c2);
            em.persist(iT1);
            em.persist(iT2);

            em.getTransaction().commit();

            System.out.println(c1.toString());
            System.out.println(c2.toString());
            System.out.println(iT1);
            System.out.println(iT2);
            System.out.println(fc1);
            System.out.println(fIT1);
            System.out.println(all);

        } finally {
            em.close();
        }

    }

}
