package basicRestAssured;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProjectTest {
    @Test
    public void verifyCreateProjectJSONObject(){

        JSONObject body = new JSONObject();
        body.put("Content","EynarCRUD");
        body.put("Icon",1);

        //create
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
                .body("Content",equalTo("EynarCRUD"))
                .body("Icon",equalTo(1));
        int idProject= response.then().extract().path("Id");

        // read
        response =given()
                .auth()
                .preemptive()
                .basic("ucb2022@ucb2022.com","12345")
                .log().all()
        .when()
                .get("https://todo.ly/api/projects/"+idProject+".json");


        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("EynarCRUD"))
                .body("Icon",equalTo(1));

        // update
        body.put("Content","CatoCRUD");
        response =given()
                .auth()
                .preemptive()
                .basic("ucb2022@ucb2022.com","12345")
                .body(body.toString())
                .log().all()
        .when()
                .put("https://todo.ly/api/projects/"+idProject+".json");


        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("CatoCRUD"))
                .body("Icon",equalTo(1));
        // delete
        response =given()
                .auth()
                .preemptive()
                .basic("ucb2022@ucb2022.com","12345")
                .log().all()
        .when()
                .delete("https://todo.ly/api/projects/"+idProject+".json");


        response.then()
                .log().all()
                .statusCode(200)
                .body("Content",equalTo("CatoCRUD"))
                .body("Icon",equalTo(1));
    }


}
