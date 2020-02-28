package rest;

import dto.PersonDTO;
import entities.Person;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1, p2, p3, p4, p5, p6;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.CREATE);

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Jessica", "Hansen", "85329473");
        p2 = new Person("Kenneth", "Andersen", "20753861");
        p3 = new Person("Harald", "Pedersen", "12345678");
        p4 = new Person("Gustav", "Knudsen", "98765438");
        p5 = new Person("Caroline", "Larsen", "39281764");
        p6 = new Person("Hanne", "Karlsen", "90897867");

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);
            em.persist(p6);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/person").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }

    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/count").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(6));
    }

    @Test
    public void testGetAll() throws Exception {
        given()
                .contentType("application/json")
                .get("/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("all", hasSize(6));
    }

//    Find ud af how
//    @Test
//    public void testEditPersonOnId() throws Exception {
//        FACADE.editPerson()
//        
//        p1.
//        given().contentType(ContentType.JSON)
//                .body(new PersonDTO(p1))
//                .when()
//                .post("person/edit/{id}", id)
//                .then()
//                .body("lastName", equalTo("Larsen"));
//    }
    
    @Test
    public void testGetPersonOnId() {
        Long id = p2.getId();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("person/id/{id}", id).then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Kenneth"))
                .body("lName", equalTo("Andersen"))
                .body("phone", equalTo("20753861"));
    }
    
    @Test
    public void testDeletePersonOnId() throws Exception {
        Long id = p5.getId();
        given()
                .contentType("application/json")
                .when()
                .put("person/delete/{id}", id).then()
                .assertThat()                                
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("fName", equalTo("Caroline"))
                .body("lName", equalTo("Larsen"))
                .body("phone", equalTo("39281764"));
        
    }
    
    @Test
    public void testAddPerson() throws Exception {
        given()
                .contentType("application/json")
                .body(new PersonDTO(new Person("Boris", "Kowalski", "47209536")))
                .when()
                .post("person/add").then()
                .body("fName", equalTo("Boris"))
                .body("lName", equalTo("Kowalski"))
                .body("phone", equalTo("47209536"))
                .body("id", notNullValue());
    }
    
}
