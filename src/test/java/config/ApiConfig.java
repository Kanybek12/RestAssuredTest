package config;

import io.restassured.RestAssured;

public class ApiConfig {
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        //       String BASE_URL = System.getProperty("baseUrl", "https://reqres.in/api");
    }

    public static void shutdown() {
        System.out.println("✅ Test finished");
    }
}


