package entities2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/*
 * @author Nina
 */
public class Tester2 {
    
    // private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");

    public static void main(String[] args) {
        Persistence.generateSchema("pu", null);
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Customer2 c1 = new Customer2("Bent", "Bentsen");
        Customer2 c2 = new Customer2("Lars", "Larsen");
        Address2 a1 = new Address2("Vejen", "Byen");
        Address2 a2 = new Address2("TheStreet", "TheCity");
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(a1);
            em.persist(a2);
            c1.addAddressToCustomer(a1);
            c2.addAddressToCustomer(a2);
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
