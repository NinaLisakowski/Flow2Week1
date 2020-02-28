package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;//= Persistence.createEntityManagerFactory("pu");
    //sEntityManager em = emf.createEntityManager();

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        //EntityManager em = emf.createEntityManager();
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getRenameMeCount() {
        EntityManager em = getEntityManager();
        try {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return renameMeCount;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        EntityManager em = getEntityManager();
        Person pers = new Person(fName, lName, phone);
        try {
            em.getTransaction().begin();
            em.persist(pers);
            em.getTransaction().commit();
            return new PersonDTO(pers);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(int id) {
        EntityManager em = getEntityManager();
        try {
            Person p = em.find(Person.class, Long.parseLong("" + id));
            if (p != null) {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) {
        EntityManager em = getEntityManager();
        try {
            Person p = em.find(Person.class, Long.parseLong("" + id));
            if (p != null) {
                return new PersonDTO(p);
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> tq = em.createQuery("SELECT p FROM Person p", Person.class);
            List<Person> qList = tq.getResultList();
            PersonsDTO pDTO = new PersonsDTO(qList);
            return pDTO;
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) {
        EntityManager em = getEntityManager();
        try {
            Person pers = em.find(Person.class, p.getId());
            em.getTransaction().begin();
            pers.setFirstName(p.getfName());
            pers.setLastName(p.getlName());
            pers.setPhone(p.getPhone());
            pers.setLastEdited(new Date());
            em.getTransaction().commit();
            return new PersonDTO(pers);
        } finally {
            em.close();
        }
    }

}
