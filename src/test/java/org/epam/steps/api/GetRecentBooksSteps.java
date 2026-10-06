package org.epam.steps.api;

import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetRecentBooksSteps extends BaseSteps {
    private static final String GET_RECENT_BOOKS_ENDPOINT = "/api/v1/books/recent";
    private Response response;
    private Response previousResponse;

    public void retrieveRecentBooks(String limit, String sortBy, String order) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("sortBy", sortBy);
        queryParams.put("order", order);
        response = sendGetRequest(GET_RECENT_BOOKS_ENDPOINT, new HashMap<>(), new HashMap<>(), queryParams);
    }

    public void retrieveBooksWithDefaultParams() {
        response = sendGetRequest(GET_RECENT_BOOKS_ENDPOINT, new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    public void storeResponseData() {
        previousResponse = response;
    }

    public void retrieveRecentBooksAgain(String limit, String sortBy, String order) {
        storeResponseData();
        retrieveRecentBooks(limit, sortBy, order);
    }

    public void validateStatusCode(int expectedCode) {
        assertThat("Response status code", response.getStatusCode(), is(equalTo(expectedCode)));
    }

    public void validateContentType(String expectedContentType) {
        assertThat("Content type", response.getContentType(), containsString(expectedContentType));
    }

    public void validateResponseTime(long maxMillis) {
        assertThat("Response time", response.getTime(), lessThan(maxMillis));
    }

    public void validateDataArrayExists() {
        assertThat("Data array", response.jsonPath().getList("data"), notNullValue());
    }

    public void validateMetadataExists() {
        assertThat("Metadata", response.jsonPath().getMap("metadata"), notNullValue());
    }

    public void validateBookFields() {
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> books = jsonPath.getList("data");
        for (Map<String, Object> book : books) {
            assertThat("Book id", book, hasKey("id"));
            assertThat("Book isbn", book, hasKey("isbn"));
            assertThat("Book title", book, hasKey("title"));
            assertThat("Book author", book, hasKey("author"));
            assertThat("Book createdAt", book, hasKey("createdAt"));
            assertThat("Book updatedAt", book, hasKey("updatedAt"));
        }
    }

    public void validateMetadataFields() {
        Map<String, Object> metadata = response.jsonPath().getMap("metadata");
        assertThat("Total field", metadata, hasKey("total"));
        assertThat("Limit field", metadata, hasKey("limit"));
        assertThat("SortBy field", metadata, hasKey("sortBy"));
        assertThat("Order field", metadata, hasKey("order"));
    }

    public void validateMetadataLimit(String expectedLimit) {
        int metadataLimit = response.jsonPath().getInt("metadata.limit");
        int expectedLimitInt = Integer.parseInt(expectedLimit);
        assertThat("Limit match", metadataLimit, equalTo(expectedLimitInt));
    }

    public void validateDataArraySize(String maxLimit) {
        List<Map<String, Object>> books = response.jsonPath().getList("data");
        int maxLimitInt = Integer.parseInt(maxLimit);
        assertThat("Array size", books.size(), lessThanOrEqualTo(maxLimitInt));
    }

    public void validateMetadataOrder(String expectedOrder) {
        String order = response.jsonPath().getString("metadata.order");
        assertThat("Order", order, equalTo(expectedOrder));
    }

    public void validateAllIDsAreUUID() {
        List<String> ids = response.jsonPath().getList("data.id");
        String pattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        for (String id : ids) {
            assertThat("UUID: " + id, p.matcher(id).matches());
        }
    }

    public void validateAllTimestampsAreISO8601() {
        List<Map<String, Object>> books = response.jsonPath().getList("data");
        for (Map<String, Object> book : books) {
            try {
                OffsetDateTime.parse((String) book.get("createdAt"));
                OffsetDateTime.parse((String) book.get("updatedAt"));
            } catch (DateTimeParseException e) {
                throw new AssertionError("Invalid timestamp format");
            }
        }
    }

    public void validateNoSensitiveData() {
        List<Map<String, Object>> books = response.jsonPath().getList("data");
        List<String> sensitiveFields = Arrays.asList("password", "secret", "token", "apiKey", "privateKey");
        for (Map<String, Object> book : books) {
            for (String field : sensitiveFields) {
                assertThat("No " + field, book, not(hasKey(field)));
            }
        }
    }
}
