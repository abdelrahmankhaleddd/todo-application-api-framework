package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Feature("Todo Feature")
public class TodoTest {

    @Story("should Be Able To Add Todo")
    @Test(description = "should Be Able To Add Todo")
    public void shouldBeAbleToAddTodo(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        Response response = TodoApi.addTodo(todo, token);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));
    }

    @Story("should Not Be Able To Add Todo When Is Completed Is Missing")
    @Test(description = "should Not Be Able To Add Todo When Is Completed Is Missing")
    public void shouldNotBeAbleToAddTodoWhenIsCompletedIsMissing(){
        Todo todo = new Todo("Learn Appium" );
        String token = UserSteps.getUserToken();
        Response response = TodoApi.addTodo(todo, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }

    @Story("should Be Able To Get A Todo By ID")
    @Test(description = "should Be Able To Get A Todo By ID")
    public void shouldBeAbleToGetATodoByID(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String id = TodoSteps.getUserId(todo, token);
        Response response = TodoApi.getTodo(token, id);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }

    @Story("should Be Able To Delete A Todo By ID")
    @Test(description = "should Be Able To Delete A Todo By ID")
    public void shouldBeAbleToDeleteTodoByID(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();
        String id = TodoSteps.getUserId(todo, token);
        Response response = TodoApi.deleteTodo(token, id);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));
    }
}
