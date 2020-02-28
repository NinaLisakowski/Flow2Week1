package facades;

import entities.Customer;
import entities.ItemType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/*
 * @author Nina
 */
public class CustomerFacade {

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;//= Persistence.createEntityManagerFactory("pu");
    //EntityManager em = emf.createEntityManager();

//    public static void main(String[] args) {
//            Persistence.generateSchema("pu", null);
//
//    }
    //Private Constructor to ensure Singleton
    private CustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        //EntityManager em = emf.createEntityManager();
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Customer createCustomer(String name, String email) {
        EntityManager em = getEntityManager();
        Customer cust = new Customer(name, email);
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

    public Customer findCustomer(int id) {
        EntityManager em = getEntityManager();
        try {
            Customer cust = em.find(Customer.class, id);
            if (cust != null) {
                return cust;
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Customer> tq = em.createQuery("SELECT c FROM Customer c", Customer.class);
            List<Customer> qList = tq.getResultList();
            return qList;
        } finally {
            em.close();
        }
    }

    public ItemType createItemType(String name, String description, int price) {
        EntityManager em = getEntityManager();
        ItemType it = new ItemType(name, description, price);
        try {
            em.getTransaction().begin();
            em.persist(it);
            em.getTransaction().commit();
            return it;
        } finally {
            em.close();
        }
    }

    public ItemType findItemType(int id) {
        EntityManager em = getEntityManager();
        try {
            ItemType it = em.find(ItemType.class, id);
            if (it != null) {
                return it;
            } else {
                return null;
            }
        } finally {
            em.close();
        }
    }

//    public Customer createOrderAddToCustomer(int orderId, String email) {
//        EntityManager em = getEntityManager();
//        PurchaseOrder po = new PurchaseOrder(orderId);
//        try {
//            em.getTransaction().begin();
//            em.persist(po);
//            em.getTransaction().commit();
//            Customer cust = em.find(Customer.class, email);
//            cust.setOrders((List<PurchaseOrder>) po);
//            return cust;
//        } finally {
//            em.close();
//        }
//    }

}
