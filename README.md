# StaffRecordChecker

A Spring Boot REST API that accepts an uploaded CSV of staff records and
returns which ones are valid, which are invalid (with a reason), and
totals for both groups.

## Endpoint

```
POST /api/v1/check
Content-Type: multipart/form-data
Param: csvFile (the CSV file)
```

**Response (200 OK):**
```json
{
  "validRecord": [
    { "name": "Tunde James", "email": "tunde@example.com", "department": "Technology" }
  ],
  "invalidRecords": [
    { "recordValues": ["Ada Bello", "ada@example.com", "Operations"], "description": "Duplicate email" }
  ],
  "validRecordCount": 2,
  "invalidRecordCount": 4
}
```

On a malformed multipart request or any other error, it returns
`400 Bad Request`.

## Project structure

```
src/main/java/com/mcmanuel/StaffRecordChecker/
 ├── StaffRecordCheckerApplication.java   # Spring Boot entry point
 ├── controller/
 │    └── RecordCheckerController.java    # POST /api/v1/check
 ├── service/
 │    └── RecordCheckerService.java       # parses the CSV and applies the validation rules
 └── model/
      ├── StaffRecord.java                # name / email / department (Lombok @Data)
      ├── InvalidRecordWithDescription.java # raw row values + why it failed
      ├── Response.java                    # valid/invalid lists + counts, returned to the caller
      └── Mapper.java                      # CSVRecord -> StaffRecord

src/main/resources/
 ├── static/
 ├── templates/
 └── application.yaml

src/test/java/...
```

## How it works

1. `RecordCheckerController` exposes `POST /api/v1/check`, taking the
   CSV as a `MultipartFile` request param named `csvFile`, and
   delegates to `RecordCheckerService`.
2. `RecordCheckerService.checkRecords()` parses the file with Apache
   Commons CSV (`CSVParser` / `CSVFormat`), then loops over each
   `CSVRecord`:
   - If the row has all 3 columns (`record.isConsistent()`), it checks
     the email: if it looks valid and isn't a duplicate of an email
     already added to the valid list, it's mapped to a `StaffRecord`
     via `Mapper` and added as valid; otherwise it's added to the
     invalid list with a description.
   - If the row is missing a column, it's treated as invalid unless a
     specific fallback condition is met.
3. Duplicates are tracked by building a running list of emails already
   accepted into the valid list and checking new emails against it.
4. The method returns a `Response` object (Lombok `@Builder`) with
   both lists and their counts, which the controller wraps in
   `ResponseEntity.ok()`.

## Requirements

- Java 17+ (matches the Spring Boot 3.x parent)
- Maven (or use the bundled `./mvnw` / `mvnw.cmd` wrapper — no local
  Maven install needed)

## Running it

```bash
./mvnw spring-boot:run
```

Then, from another terminal, upload a CSV:

```bash
curl -F "csvFile=@staff_records.csv" http://localhost:8080/api/v1/check
```

(Adjust the port if you've set a different `server.port` in
`application.yaml`.)

## Testing

I tested the endpoint by uploading the sample CSV from the assessment
brief via `curl` (and Postman) and checking that:

- Tunde James and Nneka Obi come back in `validRecord`
- Ada Bello, Mira Okafor, the blank-name row, and Sola Ade come back in
  `invalidRecords`, each with a description
- `validRecordCount` and `invalidRecordCount` match the list sizes

## Known limitations / one improvement I'd make with more time

The current email check (`isEmailValid`) matches against a fixed domain rather than validating general email *format*, and the duplicate check compares emails as-is rather than case-insensitively —
so `ADA@example.com` and `ada@example.com` wouldn't currently be
caught as duplicates. With more time I'd:

- replace the domain check with a proper `local@domain.tld` regex
- lower-case emails before comparing them for duplicates
- add a `RecordCheckerServiceTest` covering each validation rule
  directly, rather than relying on manual `curl` checks

## Documentation / AI tools used

I used Claude (Anthropic) to help write this README, and referred to the Apache Commons CSV and Spring
Boot multipart-upload documentation.
