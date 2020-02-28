package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Nina
 */
public class Tester {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("pu");

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();

        Customer c1 = new Customer("Hans", "Hansen");
        Customer c2 = new Customer("Pernille", "Pernillesen");

        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);

            c1.addHobby("Running");
            c1.addHobby("Knitting");
            c2.addHobby("Cooking");
            c2.addHobby("Singing");
            c1.addPhone("56473829", "1 PhoneNumber for c1");
            c2.addPhone("91827364", "1 PhoneNumber for c2");
            c1.addPhone("29568392", "2 PhoneNumber for c1");
            c2.addPhone("76859543", "2 PhoneNumber for c2");
            em.getTransaction().commit();

            System.out.println(c1.toString());
            System.out.println(c2.toString());

            System.out.println(c1.getHobbies());
            System.out.println(c2.getHobbies());
            System.out.println(c1.getPhoneDescription("56473829"));
            System.out.println(c2.getPhoneDescription("91827364"));

        } finally {
            em.close();
        }
    }

}
