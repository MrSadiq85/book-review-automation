# BDD Test Automation - Quick Start Guide

## What Was Created

This project now includes comprehensive BDD automation tests for the **GET Recent Books** endpoint.

### Test Files Created

1. **Feature File** (Gherkin Syntax)
   - Path: `src/test/resources/features/api/GetRecentBooks.feature`
   - Contains 16+ test scenarios
   - Written in human-readable BDD format
   - Easy for stakeholders to understand

2. **Step Implementation** (Java)
   - Path: `src/test/java/org/epam/steps/api/GetRecentBooksSteps.java`
   - Contains 20+ validation methods
   - Uses REST-assured for API calls
   - Uses Hamcrest matchers for assertions

3. **Step Definitions** (Java)
   - Path: `src/test/java/org/epam/stepdefinitions/api/GetRecentBooksStepDefinitions.java`
   - Maps Gherkin steps to Java methods
   - Connects feature file to implementation

### Documentation

- `GET_RECENT_BOOKS_BDD_TESTS.md` - Detailed test documentation
- `TEST_SCENARIOS_SUMMARY.txt` - Quick reference of all test scenarios

## Test Coverage

The test suite covers:

### 1. Happy Path
- Successful retrieval with valid parameters
- Status 200, JSON format, response time check

### 2. Query Parameters
- **limit:** Values 1, 5, 10, 20, 1000, 0, -1
- **sortBy:** Values createdAt, updatedAt, invalidField
- **order:** Values DESC, ASC, INVALID

### 3. Response Validation
- Data array structure
- Metadata structure and values
- Field presence and non-null values
- Data type validation (UUID, numeric, ISO 8601)

### 4. Edge Cases
- Empty results
- Large limits
- Zero and negative limits
- Missing parameters
- Invalid parameters

### 5. Business Logic
- Sorting verification (DESC order)
- Metadata accuracy
- Data consistency across requests

### 6. Security
- No sensitive data exposure
- Only expected fields returned
- No debug information leaked

## Running the Tests

### Execute All Tests
```bash
./gradlew test
```

### View Test Report
After running tests, open:
```
build/reports/cucumber-report.html
```

### Run with Gradle Wrapper
```bash
# Windows
gradlew.bat test

# Linux/Mac
./gradlew test
```

## Test Endpoint

```
URL: http://localhost:8080/api/v1/books/recent
Method: GET
Accept: application/json

Query Parameters:
- limit: integer (default: varies by implementation)
- sortBy: string (e.g., createdAt, updatedAt)
- order: string (ASC or DESC)
```

## Expected Response

```json
{
  "data": [
    {
      "id": "uuid-string",
      "isbn": "numeric-string",
      "title": "book-title",
      "author": "author-name",
      "createdAt": "2026-10-06T11:00:41.1755468+05:30",
      "updatedAt": "2026-10-06T11:00:41.1755468+05:30"
    }
  ],
  "metadata": {
    "total": 1,
    "limit": 1,
    "sortBy": "createdAt",
    "order": "DESC"
  }
}
```

## Project Structure

```
book-review-automation/
├── src/test/
│   ├── resources/features/api/
│   │   ├── GetRecentBooks.feature          (NEW)
│   │   └── ValidateCreateBook.feature      (existing)
│   └── java/org/epam/
│       ├── steps/api/
│       │   ├── GetRecentBooksSteps.java    (NEW)
│       │   ├── BaseSteps.java              (existing)
│       │   └── BookManagementSteps.java    (existing)
│       └── stepdefinitions/api/
│           ├── GetRecentBooksStepDefinitions.java    (NEW)
│           └── BookManagementStepDefinitions.java    (existing)
├── GET_RECENT_BOOKS_BDD_TESTS.md           (NEW - Detailed docs)
├── TEST_SCENARIOS_SUMMARY.txt              (NEW - Quick reference)
└── BDD_TEST_QUICK_START.md                 (NEW - This file)
```

## Test Statistics

- **Total Test Scenarios:** 16+
- **Validation Methods:** 20+
- **Test Data Values Covered:** 
  - Limit: 6 variations
  - SortBy: 3 variations
  - Order: 3 variations
- **Edge Cases:** 8+ covered
- **Response Validations:** 50+ assertions

## How Tests Are Organized

### By Feature
- Query Parameter Validation
- Response Structure Validation
- Data Type Validation
- Edge Case Handling
- Security Validation

### By Scenario Type
- **Outline Scenarios:** Use example tables for parameterization
- **Single Scenarios:** Test specific cases like empty results
- **Background:** Common setup for all tests

## Key Features

1. **BDD Format:** Human-readable Gherkin syntax
2. **Comprehensive:** Happy path + edge cases + security
3. **Maintainable:** Reusable step definitions
4. **Well-Documented:** Clear documentation included
5. **Modular:** Easily extensible for future tests
6. **Performance:** Response time assertions included

## Extending the Tests

To add new test scenarios:

1. Add new scenario to `GetRecentBooks.feature`
2. Implement corresponding steps in `GetRecentBooksSteps.java`
3. Add step definitions in `GetRecentBooksStepDefinitions.java`
4. Run tests with `./gradlew test`

## Dependencies Used

- **RestAssured:** 6.0.1 - REST API testing
- **Cucumber:** 8.0.4 - BDD framework
- **JUnit 5:** 6.1.3 - Test runner
- **Hamcrest:** Latest - Matcher assertions
- **SLF4J:** 2.0.18 - Logging

## Troubleshooting

### Tests Not Running
- Ensure API is running on localhost:8080
- Check Java version (requires Java 25+)
- Run `./gradlew clean test`

### Report Not Generated
- Check `build/reports/` directory
- Ensure no test failures
- Browser may need refresh

### API Connection Issues
- Verify API endpoint is accessible
- Check firewall settings
- Verify request headers match API requirements

## Next Steps

1. Run tests: `./gradlew test`
2. Review test report: `build/reports/cucumber-report.html`
3. Examine test failures (if any)
4. Integrate into CI/CD pipeline
5. Extend tests as needed

## Support

For detailed information, see:
- `GET_RECENT_BOOKS_BDD_TESTS.md` - Full documentation
- `TEST_SCENARIOS_SUMMARY.txt` - Test scenario reference

