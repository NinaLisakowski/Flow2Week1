package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * @author Nina
 */
public class Tester {

   // private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");

    public static void main(String[] args) {
        Persistence.generateSchema("pu", null);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Customer c1 = new Customer("Bent", "Bentsen");
        Customer c2 = new Customer("Lars", "Larsen");
        Address a1 = new Address("Vejen", "Byen");
        Address a2 = new Address("TheStreet", "TheCity");
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            em.getTransaction().commit();
            System.out.println(c1.toString());
            System.out.println(c2.toString());
            System.out.println(a1.toString());
            System.out.println(a2.toString());

        } finally {
            em.close();
        }
    }

}
