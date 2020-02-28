package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

/*
 * @author Nina
 */

 /*
Collections of basic types
1) In your NetBeans project, create an Entity class Customer,  with a firstName and lastName property, sufficient constructors and getters/setters. Use GenerationType.Identity strategy for the ID.

2) Provide the Customer class with a list of hobbies as sketched below:
private List<String> hobbies = new ArrayList();
Add the following methods to the class:  addHobby(String s) and String getHobbies() (returns a comma-separated list with all hobbies) 

3) Add a class, Tester.java, to test drive (manually, not with JUnit) the Customer class and create and persist a few customers with some hobbies.
Test and verify how the list is stored by the Customer table
Do you like what you see?
- It is stored as a BLOB

If not, add the following annotation to the hobbies List (do it anyway ;-)
@ElementCollection
Regenerate (run the project) tables and observe the result. we assume you agree that this is so much better ;-)
- It creates a new table "Customer_HOBBIES" containing a Customer_ID and HOBBIES.


Maps of Basic Types
Add a map to your Customer class as sketched below:
private Map<String,String> phones = new HashMap();
Add the following methods to the class:
addPhone(String phoneNo, String description){..}
getPhoneDescription(String phoneNo){..}
Add a few phone numbers to your customer, in the Tester class, and execute (which should regenerate the tables).
Bloooob, do you like what you see?
- It returns BLOB

If not, add the following annotations to the map:
@ElementCollection(fetch = FetchType.LAZY)
@MapKeyColumn(name = "PHONE")
@Column(name="Description")
Execute and observe the generated columns and values. Make sure you understand the purpose of each of the annotations
-   @ElementCollection(fetch = FetchType.LAZY) 
        ==> The @ElementCollection still does as above, where it creates the new table and not the BLOB.
            Lazy fetch type fetches the data whenever call getter method of the object.
    @MapKeyColumn(name = "PHONE")
        ==> The MapKeyColumn is used to specify the database column which stores the key of a map where the key is a basic type.
    @Column(name="Description")
        ==> This annotation is for to specifying the mapping between a basic entity attribute and the database table column.
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @ElementCollection
    private List<String> hobbies = new ArrayList();
    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name = "PHONE")
    @Column(name = "Description")
    private Map<String, String> phones = new HashMap();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    void addHobby(String s) {
        hobbies.add(s);
    }

    String getHobbies() {
        StringJoiner sj = new StringJoiner(", ");
        for (String hobby : hobbies) {
            sj.add(hobby);
        }
        return sj.toString();
    }

    void addPhone(String phoneNo, String description) {
        phones.put(phoneNo, description);
    }

    String getPhoneDescription(String phoneNo) {
        return phones.get(phoneNo);
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", hobbies=" + hobbies + '}';
    }

}
