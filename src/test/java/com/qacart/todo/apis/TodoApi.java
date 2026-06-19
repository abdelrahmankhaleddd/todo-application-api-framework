package com.qacart.todo.apis;

import com.qacart.todo.base.Specification;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class TodoApi {

    public static Response addTodo(Todo todo , String token){
        return given()
                .spec(Specification.getRequestSpecs())
                .body(todo)
                .auth().oauth2(token)
                .when().post(Route.TODO_PATH)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getTodo(String token , String id){
        return given()
                .spec(Specification.getRequestSpecs())
                .auth().oauth2(token)
                .when().get(Route.TODO_PATH +"/" + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodo(String token , String id){
        return given()
                .spec(Specification.getRequestSpecs())
                .auth().oauth2(token)
                .when().delete(Route.TODO_PATH +"/" + id)
                .then()
                .log().all()
                .extract().response();
    }

}
