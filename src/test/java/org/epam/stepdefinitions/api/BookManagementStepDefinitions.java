package org.epam.stepdefinitions.api;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.epam.steps.api.BookManagementSteps;

public class BookManagementStepDefinitions {

    BookManagementSteps bookManagementSteps = new BookManagementSteps();


    @When("User creates a book")
    public void userCreatesABook(DataTable dataTable) {
        bookManagementSteps.createBook(dataTable);
    }


    @Then("the response status code should be {int} response time less than {int} millis and content type should be {string}")
    public void validateResponse(int statusCode, int responseTime, String contentType) {
        bookManagementSteps.validateResponse(statusCode, responseTime, contentType);
    }

    @And("User deletes the book with id {string}")
    public void userDeletesTheBookWithId(String id) {
       bookManagementSteps.deleteBook(id);
    }
}
