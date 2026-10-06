package org.epam.steps.api;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BookManagementSteps extends BaseSteps {
    private static final String CREATE_BOOK_ENDPOINT = "/api/v1/books";
    private static final String DELETE_BOOK_ENDPOINT = "/api/v1/books/{id}";
    private Response response;

    public void createBook(DataTable dataTable) {
        // Convert DataTable to a Map
        Map<String, String> bookData = dataTable.asMap(String.class, String.class);

        // Send POST request to create a book
        response = sendPOSTRequest(CREATE_BOOK_ENDPOINT, bookData, emptyMap(), emptyMap(), emptyMap());
    }

    public void deleteBook(String id) {
        if(id.equals("dynamic")) {
            // Extract the book ID from the response of the createBook method
            id = response.jsonPath().getString("id");
        }
        // Send DELETE request to delete a book
        response = sendDELETERequest(DELETE_BOOK_ENDPOINT, emptyMap(), buildPathParams(id), emptyMap());
    }

    public void validateResponse(int statusCode, int responseTime, String contentType) {
        // Validate the response status code
        assertThat("Response status code does not match", response.getStatusCode(), is(equalTo(statusCode)));

        // Validate the response time
        assertThat("Response time exceeds the expected limit", response.getTime(), is(lessThan((long) responseTime)));

        // Validate the content type
        assertThat("Response content type does not match", response.getContentType(), is(equalTo(contentType)));
    }

    private Map<String, String> buildPathParams(String id) {
        return Map.of("id", id);
    }

    private Map<String, String> emptyMap() {
        return Map.of();
    }


}
