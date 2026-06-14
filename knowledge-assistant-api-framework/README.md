# Knowledge Assistant API — REST Assured Data-Driven Framework

REST Assured + TestNG framework for the [Knowledge Assistant API](https://main-knowledge-assistant.newpage.workers.dev/docs) (OpenAPI 3.0).

## API Under Test

| Method | Endpoint    | Description                          |
|--------|-------------|--------------------------------------|
| POST   | `/query`    | Ask the assistant a question         |
| GET    | `/documents`| List document metadata for the user  |

**Required headers (both endpoints):**
- `X-User-Region`: `Americas` | `EMEA` | `APAC`
- `X-User-Role`: `Employee` | `Engineering` | `Finance` | `Manager`

---

## Folder Structure

```
knowledge-assistant-api-framework/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/ka/api/
    │   │   ├── config/
    │   │   │   └── ApiConfig.java              # Loads base URI & timeouts
    │   │   ├── constants/
    │   │   │   ├── ApiEndpoints.java           # /query, /documents
    │   │   │   └── ApiHeaders.java             # Header name constants
    │   │   ├── enums/
    │   │   │   ├── UserRegion.java
    │   │   │   ├── UserRole.java
    │   │   │   └── DocumentState.java
    │   │   ├── models/
    │   │   │   ├── request/
    │   │   │   │   └── QueryRequest.java
    │   │   │   ├── response/
    │   │   │   │   ├── QueryResponse.java
    │   │   │   │   └── DocumentMetadata.java
    │   │   │   └── testdata/
    │   │   │       └── TestCaseData.java       # JSON test data POJO
    │   │   ├── clients/
    │   │   │   ├── QueryApiClient.java         # POST /query wrapper
    │   │   │   └── DocumentsApiClient.java     # GET /documents wrapper
    │   │   ├── specs/
    │   │   │   └── RequestSpecFactory.java     # RestAssured request specs
    │   │   └── utils/
    │   │       ├── TestDataLoader.java         # Reads JSON test files
    │   │       ├── JsonDataProvider.java       # TestNG @DataProvider
    │   │       ├── TestDataFile.java           # Annotation for data file path
    │   │       └── ResponseValidator.java      # Status, schema, body checks
    │   └── resources/config/
    │       └── application.properties
    └── test/
        ├── java/com/ka/api/
        │   ├── base/
        │   │   └── BaseApiTest.java
        │   └── tests/
        │       ├── QueryApiPositiveTest.java
        │       ├── QueryApiNegativeTest.java
        │       ├── QueryApiSecurityTest.java
        │       ├── DocumentsApiPositiveTest.java
        │       └── DocumentsApiNegativeTest.java
        └── resources/
            ├── testng.xml
            ├── schemas/
            │   ├── query_response_schema.json
            │   └── documents_response_schema.json
            └── testdata/
                ├── query/
                │   ├── query_positive_tests.json
                │   ├── query_negative_tests.json
                │   └── query_security_matrix.json
                └── documents/
                    ├── documents_positive_tests.json
                    └── documents_negative_tests.json
```

---

## Test Case Matrix (46 total)

### POST /query — Positive (8)

| ID        | Scenario |
|-----------|----------|
| Q-POS-001 | Americas + Employee + policy question |
| Q-POS-002 | EMEA + Engineering + technical question |
| Q-POS-003 | APAC + Finance + finance question |
| Q-POS-004 | Americas + Manager + management question |
| Q-POS-005 | Special characters in question |
| Q-POS-006 | Long question text |
| Q-POS-007 | APAC + Employee matrix |
| Q-POS-008 | EMEA + Finance matrix |

### POST /query — Negative (14)

| ID        | Scenario |
|-----------|----------|
| Q-NEG-001 | Missing `X-User-Region` |
| Q-NEG-002 | Missing `X-User-Role` |
| Q-NEG-003 | Invalid region value |
| Q-NEG-004 | Invalid role value |
| Q-NEG-005 | Missing `question` field |
| Q-NEG-006 | Empty question |
| Q-NEG-007 | Whitespace-only question |
| Q-NEG-008 | Null question |
| Q-NEG-009 | Empty request body |
| Q-NEG-010 | Malformed JSON |
| Q-NEG-011 | Both headers missing |
| Q-NEG-012 | Lowercase region (case sensitivity) |
| Q-NEG-013 | Lowercase role (case sensitivity) |
| Q-NEG-014 | Wrong Content-Type |

### POST /query — Security / Access (4)

| ID        | Scenario |
|-----------|----------|
| Q-SEC-001 | Americas Employee scoped query |
| Q-SEC-002 | EMEA Engineering scoped query |
| Q-SEC-003 | APAC Finance scoped query |
| Q-SEC-004 | Manager elevated visibility |

### GET /documents — Positive (14)

| ID        | Scenario |
|-----------|----------|
| D-POS-001 | Americas + Employee — list all |
| D-POS-002 | EMEA + Engineering — list all |
| D-POS-003 | APAC + Finance — list all |
| D-POS-004 | Americas + Manager — list all |
| D-POS-005 | Filter by region=Americas |
| D-POS-006 | Filter by region=EMEA |
| D-POS-007 | Filter by audience=Employee |
| D-POS-008 | Filter by audience=Engineering |
| D-POS-009 | Filter by state=Approved |
| D-POS-010 | Filter by state=Draft |
| D-POS-011 | Filter by state=In Review |
| D-POS-012 | Filter by state=Retired |
| D-POS-013 | Combined region + state |
| D-POS-014 | Combined region + audience + state |

### GET /documents — Negative (10)

| ID        | Scenario |
|-----------|----------|
| D-NEG-001 | Missing `X-User-Region` |
| D-NEG-002 | Missing `X-User-Role` |
| D-NEG-003 | Invalid region |
| D-NEG-004 | Invalid role |
| D-NEG-005 | Both headers missing |
| D-NEG-006 | Invalid state query param |
| D-NEG-007 | Lowercase region header |
| D-NEG-008 | Lowercase role header |
| D-NEG-009 | Empty region header |
| D-NEG-010 | Empty role header |

---

## How to Run

```bash
cd knowledge-assistant-api-framework
mvn clean test
```

Or in Eclipse: right-click `src/test/resources/testng.xml` → **Run As → TestNG Suite**.

---

## Data-Driven Design

1. Test scenarios live in JSON under `src/test/resources/testdata/`
2. `@TestDataFile` annotation points each test method to its JSON file
3. `JsonDataProvider` loads JSON and feeds TestNG `@DataProvider`
4. API calls go through client classes (not raw RestAssured in tests)
5. Assertions centralized in `ResponseValidator`

To add a new test: add a JSON object to the relevant file — no Java code change needed.

---

## Notes

- Expected status codes for negative tests follow OpenAPI required-field semantics; adjust `expectedStatusCode` in JSON if the live API returns different codes (e.g. 422 vs 400).
- JSON schemas validate contract compliance for 200 responses.
- Base URI is configurable in `src/main/resources/config/application.properties`.
