package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiContext {
    private static final String BASE_URL = "https://dummyapi.io/data/v1";
    private static final String APP_ID = "63a804408eb0cb069b57e43a";

    private Response lastResponse;
    private String appIdHeader;
    private String createdUserId;

    public ApiContext() {
        this.appIdHeader = APP_ID;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public String getAppIdHeader() {
        return appIdHeader;
    }

    public void setAppIdHeader(String appId) {
        this.appIdHeader = appId;
    }

    public void clearAppIdHeader() {
        this.appIdHeader = null;
    }

    public Response getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Response response) {
        this.lastResponse = response;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String userId) {
        this.createdUserId = userId;
    }
}
