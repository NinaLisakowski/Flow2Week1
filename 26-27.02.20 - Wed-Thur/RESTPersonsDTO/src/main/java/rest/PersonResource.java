package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.PersonNotFoundException;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addPerson(String person) {
        PersonDTO pCon = GSON.fromJson(person, PersonDTO.class);
        pCon = FACADE.addPerson(pCon.getfName(), pCon.getlName(), pCon.getPhone());
        return GSON.toJson(pCon);
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonOnId(@PathParam("id") Integer id) throws PersonNotFoundException {
        PersonDTO pDTO = FACADE.getPerson(id);
//  Comment this back and test that the 500 exception also gets caught        
//        if (id == 13) {
//            System.out.println(13/0);
//        }
        if (pDTO == null) {
            throw new PersonNotFoundException("No person with provided id found");
        }
        return GSON.toJson(pDTO);
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() throws PersonNotFoundException {
        PersonsDTO psDTO = FACADE.getAllPersons();
        return GSON.toJson(psDTO);
    }

    @PUT
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletePersonOnId(@PathParam("id") Integer id) throws PersonNotFoundException {
        PersonDTO pDTO = FACADE.deletePerson(id);
        if (pDTO == null) {
            throw new PersonNotFoundException("Could not delete, provided id does not exist");
        }
        return GSON.toJson(pDTO);
    }

    @PUT
    @Path("edit/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String editPersonOnId(String personInfo, @PathParam("id") Long id) {
        PersonDTO pCon = GSON.fromJson(personInfo, PersonDTO.class);
        pCon.setId(id);
        pCon = FACADE.editPerson(pCon);
        return GSON.toJson(pCon);
    }

}
