package uhk.fim.thesis.is_central_server_rest_api.service;

import org.springframework.stereotype.Component;

@Component
public class TestConnectionResponse {

    private String testData;

    public TestConnectionResponse() {}

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }
}
