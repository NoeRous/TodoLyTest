package basicRestAssured;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreateProjectCheckTest {

    @Test
    public void verifyCreateProjectJSONObject(){

        JSONObject body = new JSONObject();
        body.put("Content","EynarJSON");
        body.put("Icon",1);

        Response response =given()
                .auth()
                .preemptive()
                .basic("ucb2022@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
        .when()
                .post("https://todo.ly/api/projects.json");


        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("EynarJSON"))
                .body("Icon",equalTo(1));

    }


    @Test
    public void verifyCreateProjectSCHEMA(){

        JSONObject body = new JSONObject();
        body.put("Content","EynarJSON33");
        body.put("Icon",1);

        Response response =given()
                .auth()
                .preemptive()
                .basic("ucb2022@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/projects.json");


        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("EynarJSON33"))
                .body("Icon",equalTo(1));

        // verificacion por schema
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(ValidationConfiguration.newBuilder().setDefaultVersion(
                        SchemaVersion.DRAFTV4
                ).freeze()).freeze();

        response.then()
                .body(matchesJsonSchemaInClasspath("schemaCreateResponse2.json")
                .using(jsonSchemaFactory));

        int id=response.then().extract().path("Id");
        String nameProject= response.then().extract().path("Content");

        System.out.println("************* ID: "+id);
        System.out.println("************* NAME_PROJECT: "+nameProject);


    }

}
