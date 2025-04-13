package com.reportportal.api;

import com.reportportal.utils.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseAPITest {

    protected static final String BASE_URL = ConfigLoader.getProperty("api.base.url");
    protected static final String API_TOKEN = ConfigLoader.getProperty("api.token");
    protected static final String PROJECT_NAME = ConfigLoader.getProperty("project.name");

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    protected static RequestSpecification getAuthenticatedRequest() {
        return RestAssured.given()
                .auth().oauth2(API_TOKEN)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("projectName", PROJECT_NAME);
    }
}