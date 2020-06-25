package apiTest;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
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
                .body(parans("Teste via API", increaseDate()))
                .log().all()
                .when()
                .post("/todo")
                .then()
                .log().all()
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

    public String increaseDate(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.add(Calendar.DATE, 1);  // number of days to add
       return new String(sdf.format(c.getTime()));
    }



}
