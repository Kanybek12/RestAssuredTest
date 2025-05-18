package specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

public class ResponseSpecs {

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();
    }

    public static ResponseSpecification createdResponse() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
    public static ResponseSpecification singleUserNotFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification deleteUser() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }
    public static ResponseSpecification MissingAndBadRequest() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

}