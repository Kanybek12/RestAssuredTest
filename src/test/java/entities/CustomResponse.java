package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomResponse {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;

    // This should be List<User> for user data
    private List<User> data;  // Changed from 'responses' to match common API conventions

    // Support object (common in many APIs)
    private Support support;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private int id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
    }

    @Data
    public static class Support {
        private String url;
        private String text;
    }
}

