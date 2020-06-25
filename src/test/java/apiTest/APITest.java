package apiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.rmi.transport.ObjectTable;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.is;

public class APITest {


    @BeforeClass
    public static void setup(){
        baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefas(){
        given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveAdicionarTarefaComSucesso(){
        given()
                .contentType(ContentType.JSON)
                .body(parans("Teste via API", "2020-06-23"))
                .when()
                .post("/todo")
                .then()
                .statusCode(201)
        ;
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida(){
        given()
                .contentType(ContentType.JSON)
                .body(parans("Teste via API", "2010-12-30"))
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", is("Due date must not be in past"))
        ;
    }

    public Map<String, Object> parans (String taskName, String dueDate){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("task", taskName);
        param.put("dueDate", dueDate);
        return param;
    }



}
