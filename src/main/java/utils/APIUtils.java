package utils;

import com.relevantcodes.extentreports.LogStatus;
import constants.Constants;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static base.TestBase.report;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;

import java.io.File;


public class APIUtils {
    public static Response postRepo(String token, String url, JSONObject body) {
        return given()
                .contentType(ContentType.JSON)
                .header(Constants.AUTHORIZATION, "Bearer " + token)
                .body(body)
                .log().all()
                .when()
                .post(url);
    }

    public static Response deleteRepo(String token, String url, String owner, String repo) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, "Bearer " + token)
                .log().all()
                .when()
                .delete(url);
    }
    public static Response getRepo(String token, String url, String owner, String repo) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, "Bearer " + token)
                .log().all()
                .when()
                .get(url);
    }

    public static Response patchRepo(String token, String url, String owner, String repo,JSONObject body) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("owner_name",owner)
                .pathParam ("repo_name",repo)
                .header(Constants.AUTHORIZATION, "Bearer " + token)
                .body(body)
                .log().all()
                .when()
                .patch(url);
    }
    public static boolean validateJsonSchema(Response response, String schemaPath) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    public static String getValueByJsonPath(String json, String jsonPathExpression) {
        JsonPath jsonPath = new JsonPath(json);
        return jsonPath.getString(jsonPathExpression);
    }
}