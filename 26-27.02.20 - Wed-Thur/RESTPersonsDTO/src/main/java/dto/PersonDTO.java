package dto;

import entities.Person;

/**
 *
 * @author Nina
 */
public class PersonDTO {

    private Long id;
    private String fName;
    private String lName;
    private String phone;

    public PersonDTO() {}

    public PersonDTO(Person p) {
        this.id = p.getId();
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.phone = p.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", fName=" + fName + ", lName=" + lName + ", phone=" + phone + '}';
    }
}
