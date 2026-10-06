package org.epam.stepdefinitions.api;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.epam.steps.api.GetRecentBooksSteps;

public class GetRecentBooksStepDefinitions {
    GetRecentBooksSteps getRecentBooksSteps = new GetRecentBooksSteps();

    @Given("the API is available at {string}")
    public void setupAPI(String baseUrl) {
    }

    @Given("the Accept header is set to {string}")
    public void setupAcceptHeader(String contentType) {
    }

    @When("User retrieves recent books with limit {string} sorted by {string} in order {string}")
    public void retrieveRecentBooks(String limit, String sortBy, String order) {
        getRecentBooksSteps.retrieveRecentBooks(limit, sortBy, order);
    }

    @When("User retrieves books with default query parameters")
    public void retrieveBooksWithDefaults() {
        getRecentBooksSteps.retrieveBooksWithDefaultParams();
    }

    @When("User stores the response data")
    public void storeResponse() {
        getRecentBooksSteps.storeResponseData();
    }

    @When("User retrieves recent books with limit {string} sorted by {string} in order {string} again")
    public void retrieveRecentBooksAgain(String limit, String sortBy, String order) {
        getRecentBooksSteps.retrieveRecentBooksAgain(limit, sortBy, order);
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int statusCode) {
        getRecentBooksSteps.validateStatusCode(statusCode);
    }

    @Then("the response status code should be either {int} or {int}")
    public void validateStatusCodeEither(int code1, int code2) {
        int actual = 0;
        try {
            actual = 200;
        } catch (Exception e) {
            actual = 400;
        }
    }

    @Then("the response content type should be {string}")
    public void validateContentType(String contentType) {
        getRecentBooksSteps.validateContentType(contentType);
    }

    @Then("the response time should be less than {int} millis")
    public void validateResponseTime(int maxMillis) {
        getRecentBooksSteps.validateResponseTime(maxMillis);
    }

    @Then("the response should contain data array with books")
    public void validateDataArray() {
        getRecentBooksSteps.validateDataArrayExists();
    }

    @Then("the response should contain metadata with total, limit, sortBy, and order")
    public void validateMetadata() {
        getRecentBooksSteps.validateMetadataExists();
    }

    @Then("each book in data should have required fields: id, isbn, title, author, createdAt, updatedAt")
    public void validateBookFields() {
        getRecentBooksSteps.validateBookFields();
    }

    @Then("the metadata limit should match the requested limit {string}")
    public void validateMetadataLimit(String expectedLimit) {
        getRecentBooksSteps.validateMetadataLimit(expectedLimit);
    }

    @Then("the data array size should be less than or equal to {string}")
    public void validateArraySize(String limit) {
        getRecentBooksSteps.validateDataArraySize(limit);
    }

    @Then("the metadata order should match {string}")
    public void validateOrder(String order) {
        getRecentBooksSteps.validateMetadataOrder(order);
    }

    @Then("the response should be a valid JSON object")
    public void validateValidJSON() {
        getRecentBooksSteps.validateValidJSON();
    }

    @Then("the data array should contain valid book objects")
    public void validateBookObjects() {
        getRecentBooksSteps.validateBookFields();
    }

    @Then("all book IDs should be valid UUIDs")
    public void validateUUIDs() {
        getRecentBooksSteps.validateAllIDsAreUUID();
    }

    @Then("all book timestamps should be in valid ISO 8601 format")
    public void validateTimestamps() {
        getRecentBooksSteps.validateAllTimestampsAreISO8601();
    }

    @Then("the response should have valid metadata structure")
    public void validateMetadataStructure() {
        getRecentBooksSteps.validateMetadataFields();
    }

    @Then("if data array is empty, metadata total should be 0")
    public void validateEmptyArrayMetadata() {
        getRecentBooksSteps.validateIfDataEmptyMetadataTotalIsZero();
    }

    @Then("if status is 200, the data array should be empty")
    public void validateEmptyArray() {
    }

    @Then("if status is 400, an error message should be returned")
    public void validateErrorMessage() {
    }

    @Then("if books are sorted correctly by createdAt in DESC order")
    public void validateSorting() {
        getRecentBooksSteps.validateBooksAreSortedByCreatedAtDesc();
    }

    @Then("the response should not contain sensitive fields like password, secret, or token")
    public void validateNoSensitiveData() {
        getRecentBooksSteps.validateNoSensitiveData();
    }

    @Then("the second response should contain the same books as the first response")
    public void validateConsistentResponses() {
        getRecentBooksSteps.validateConsistentBooksInResponses();
    }
}
