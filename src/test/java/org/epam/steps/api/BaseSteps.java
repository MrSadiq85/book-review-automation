package org.epam.steps.api;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BaseSteps {

    private RequestSpecification buildRequest() {
        return RestAssured.requestSpecification.baseUri("http://localhost:8080")
                 .given()
                 .log().all()
                 .accept(ContentType.JSON)
                 .contentType(ContentType.JSON);
    }

    protected Response sendGetRequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
        return buildRequest()
                .relaxedHTTPSValidation()
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .get(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    protected Response sendPOSTRequest(String endpoint, Object body, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
        return buildRequest()
                .relaxedHTTPSValidation()
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .extract().response();
    }

    protected Response sendDELETERequest(String endpoint, Map<String, String> headers, Map<String, String> pathParams, Map<String, String> queryParams) {
        return buildRequest()
                .relaxedHTTPSValidation()
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .when()
                .delete(endpoint)
                .then()
                .log().all()
                .extract().response();
    }


}
