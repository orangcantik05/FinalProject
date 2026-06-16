package steps;

import api.ApiContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class ApiSteps {

    private ApiContext context = new ApiContext();

    @Given("header app-id telah disetel dengan nilai {string}")
    public void headerAppIdDisetel(String appId) {
        context.setAppIdHeader(appId);
        System.out.println("[API] app-id header set to: " + appId);
    }

    @Given("header app-id tidak disetel")
    public void headerAppIdTidakDisetel() {
        context.clearAppIdHeader();
        System.out.println("[API] app-id header cleared (negative test)");
    }

    @When("saya mengirim GET request ke {string}")
    public void kirimGetRequest(String endpoint) {
        RequestSpecification request = RestAssured.given()
                .baseUri(context.getBaseUrl())
                .contentType(ContentType.JSON);
        if (context.getAppIdHeader() != null) {
            request = request.header("app-id", context.getAppIdHeader());
        }
        String resolvedEndpoint = endpoint;
        if (context.getCreatedUserId() != null) {
            resolvedEndpoint = endpoint.replace("{createdUserId}", context.getCreatedUserId());
        }
        Response response = request.get(resolvedEndpoint);
        context.setLastResponse(response);
        System.out.println("[API] GET " + resolvedEndpoint + " -> Status: " + response.getStatusCode());
        System.out.println("[API] Response body: " + response.getBody().asString().substring(0, Math.min(200, response.getBody().asString().length())));
    }

    // STEP BARU -- ditambahkan di sini
    @When("saya menyimpan ID user pertama dari daftar")
    public void simpanIdUserPertama() {
        Response listResponse = RestAssured.given()
                .baseUri(context.getBaseUrl())
                .header("app-id", context.getAppIdHeader())
                .get("/user");
        String firstId = listResponse.jsonPath().getString("data[0].id");
        context.setCreatedUserId(firstId);
        System.out.println("[API] First user ID saved: " + firstId);
    }

    @When("saya mengirim POST request ke {string} dengan body:")
    public void kirimPostRequest(String endpoint, String body) {
        String randomEmail = "test" + new Random().nextInt(99999) + "@example.com";
        String processedBody = body.replace("budi.santoso@example.com", randomEmail)
                                   .replace("test@example.com", randomEmail);

        RequestSpecification request = RestAssured.given()
                .baseUri(context.getBaseUrl())
                .contentType(ContentType.JSON);
        if (context.getAppIdHeader() != null) {
            request = request.header("app-id", context.getAppIdHeader());
        }
        Response response = request.body(processedBody).post(endpoint);
        context.setLastResponse(response);
        System.out.println("[API] POST " + endpoint + " -> Status: " + response.getStatusCode());
        System.out.println("[API] Response body: " + response.getBody().asString().substring(0, Math.min(200, response.getBody().asString().length())));

        if (response.getStatusCode() == 200) {
            String userId = response.jsonPath().getString("id");
            if (userId != null) {
                context.setCreatedUserId(userId);
                System.out.println("[API] Created user ID: " + userId);
            }
        }
    }

    @When("saya mengirim PUT request ke {string} dengan body:")
    public void kirimPutRequest(String endpoint, String body) {
        String resolvedEndpoint = endpoint;
        if (context.getCreatedUserId() != null) {
            resolvedEndpoint = endpoint.replace("{createdUserId}", context.getCreatedUserId());
        }
        Response response = RestAssured.given()
                .baseUri(context.getBaseUrl())
                .header("app-id", context.getAppIdHeader())
                .contentType(ContentType.JSON)
                .body(body)
                .put(resolvedEndpoint);
        context.setLastResponse(response);
        System.out.println("[API] PUT " + resolvedEndpoint + " -> Status: " + response.getStatusCode());
    }

    @Given("saya membuat user baru untuk diupdate")
    public void buatUserBaruUntukDiupdate() {
        String randomEmail = "update" + new Random().nextInt(99999) + "@example.com";
        String body = "{\"firstName\": \"ToUpdate\", \"lastName\": \"User\", \"email\": \"" + randomEmail + "\"}";
        Response response = RestAssured.given()
                .baseUri(context.getBaseUrl())
                .header("app-id", context.getAppIdHeader())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/user/create");
        context.setLastResponse(response);
        String userId = response.jsonPath().getString("id");
        context.setCreatedUserId(userId);
        System.out.println("[API] Pre-created user ID for update: " + userId);
    }

    @Then("response status code adalah {int}")
    public void verifikasiStatusCode(int expectedStatus) {
        int actualStatus = context.getLastResponse().getStatusCode();
        System.out.println("[API] Verifying status: expected=" + expectedStatus + ", actual=" + actualStatus);
        assertEquals("Status code mismatch!", expectedStatus, actualStatus);
    }

    @Then("response body mengandung field {string}")
    public void verifikasiFieldAda(String field) {
        String responseBody = context.getLastResponse().getBody().asString();
        assertTrue("Field '" + field + "' tidak ditemukan dalam response body!",
                responseBody.contains("\"" + field + "\""));
    }

    @Then("response body field {string} bernilai {string}")
    public void verifikasiNilaiField(String field, String expectedValue) {
        String actualValue = context.getLastResponse().jsonPath().getString(field);
        System.out.println("[API] Verifying field '" + field + "': expected='" + expectedValue + "', actual='" + actualValue + "'");
        assertEquals("Nilai field '" + field + "' tidak sesuai!", expectedValue, actualValue);
    }

    @Then("jumlah data user lebih dari {int}")
    public void jumlahDataLebihDari(int minCount) {
        List<?> dataList = context.getLastResponse().jsonPath().getList("data");
        assertNotNull("Field 'data' null!", dataList);
        assertTrue("Jumlah data tidak lebih dari " + minCount + ", actual: " + dataList.size(),
                dataList.size() > minCount);
        System.out.println("[API] Data count: " + dataList.size());
    }

    @Then("jumlah data dalam list tidak melebihi {int}")
    public void jumlahDataTidakMelebihi(int maxCount) {
        List<?> dataList = context.getLastResponse().jsonPath().getList("data");
        assertNotNull("Field 'data' null!", dataList);
        assertTrue("Jumlah data melebihi " + maxCount + ", actual: " + dataList.size(),
                dataList.size() <= maxCount);
        System.out.println("[API] Data count within limit: " + dataList.size() + " <= " + maxCount);
    }
}
