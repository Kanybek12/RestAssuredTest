[1mdiff --git a/src/test/java/runners/TestRunner.java b/src/test/java/runners/TestRunner.java[m
[1mindex bdf9e57..ac05809 100644[m
[1m--- a/src/test/java/runners/TestRunner.java[m
[1m+++ b/src/test/java/runners/TestRunner.java[m
[36m@@ -7,14 +7,15 @@[m [mimport org.junit.runner.RunWith;[m
 [m
 @RunWith(Cucumber.class)[m
 @CucumberOptions([m
[31m-        features = "classpath:features",[m
[31m-        glue = "com.example.AuthorizationSteps", //find properly way org.example.stepdef[m
[32m+[m[32m        features = "src/test/resources/features",[m
[32m+[m[32m        glue = "stepDefinition", //find properly way org.example.stepdef[m
         plugin = {[m
                 "pretty",[m
                 "html:target/cucumber-reports.html",[m
                 "json:target/cucumber.json"[m
         },[m
[31m-        tags = "@registration"[m
[32m+[m[32m     //   monochrome = true,[m
[32m+[m[32m        tags = ""[m
 )[m
 public class TestRunner {[m
 }[m
[1mdiff --git a/src/test/java/specifications/RequestSpecs.java b/src/test/java/specifications/RequestSpecs.java[m
[1mindex ba9842a..ef9c8d6 100644[m
[1m--- a/src/test/java/specifications/RequestSpecs.java[m
[1m+++ b/src/test/java/specifications/RequestSpecs.java[m
[36m@@ -9,6 +9,7 @@[m [mpublic class RequestSpecs {[m
 [m
     public static RequestSpecification authenticatedRequest() {[m
         return new RequestSpecBuilder()[m
[32m+[m[32m                .setBaseUri("https://reqres.in/api")[m
                 .addHeader("x-api-key", TestConfig.API_KEY)[m
                 .addHeader("Content-Type", "application/json")[m
                 //.log(LogDetail.BODY)[m
[1mdiff --git a/src/test/java/test/UsersTest.java b/src/test/java/test/UsersTest.java[m
[1mindex 89852c4..8a70bae 100644[m
[1m--- a/src/test/java/test/UsersTest.java[m
[1m+++ b/src/test/java/test/UsersTest.java[m
[36m@@ -350,7 +350,7 @@[m [mpublic class UsersTest {[m
 [m
     }[m
 [m
[31m-    @DisplayName("SIngle user *test*")[m
[32m+[m[32m    @DisplayName("Single user *test*")[m
     @Test[m
     public void testGetSingleUser() {[m
         Response response = given()[m
