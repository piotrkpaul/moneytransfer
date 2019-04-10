package pl.mqb.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.mqb.TestUtils;
import pl.mqb.model.Account;

import javax.ws.rs.core.Application;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AccountControllerTest extends JerseyTest {


    private static final String ID = "id";
    private static final String BALANCE = "balance";

    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9998;
    }

    @Before
    public void initializeData() {
        TestUtils.initializeTestData();
    }

    @Test
    public void validCallShouldReturn_200() {
        expect().statusCode(200).contentType(ContentType.JSON)
                .when().get("/accounts/1");
    }

    @Test
    public void nonExistingAccountShouldReturn_204_NoContentStatus() {
        expect().statusCode(204)
                .when().get("/accounts/100");
    }

    @Test
    public void shouldReturnAllAccounts() {
        given().when()
                    .get("/accounts")
                .then()
                    .body("size()", is(3));
    }

    @Test
    public void shouldReturnSpecificAccountExactData() {
        given().when()
                    .get("/accounts/1")
                .then()
                    .body(ID, equalTo("1"))
                    .body(BALANCE, equalTo(100.1f));
    }

    @Test
    public void shouldBeAbleToAddNewAccount() {
        Account newAccount = new Account("4", "5.0");

        given().contentType("application/json").body(newAccount)
                .when()
                    .post("/accounts")
                .then()
                    .body(ID, equalTo("4"))
                    .body(BALANCE, equalTo(5.0f));
    }

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(AccountController.class);
    }
}