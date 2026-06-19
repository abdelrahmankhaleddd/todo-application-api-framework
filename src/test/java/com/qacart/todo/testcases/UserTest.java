package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@Feature("User Feature")
public class UserTest {

    @Story("should Be Able To Registe")
    @Test(description = "should Be Able To Register")
    public void shouldBeAbleToRegister(){
        User user = UserSteps.generateUser();
        Response response = UserApi.register(user);
        User returedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returedUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Story("should Not Be Able To Register With The Same Email")
    @Test(description = "should Not Be Able To Register With The Same Email")
    public void shouldNotBeAbleToRegisterWithTheSameEmail(){
        User user = UserSteps.getRegisteredUser();
        Response response = UserApi.register(user);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));
    }

    @Story("should Not Be Able To Login")
    @Test(description = "should Be Able To Login")
    public void shouldBeAbleToLogin(){
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), user.getPassword());
        Response response = UserApi.login(loginData);
        User returedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(returedUser.getFirstName(),  not(equalTo(null)));
    }

    @Story("should Not Be Able To Login If The Password Is Not Correct")
    @Test(description = "should Not Be Able To Login If The Password Is Not Correct")
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect(){
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), "12345");
        Response response = UserApi.login(loginData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PASSWORD_IS_WRONG));
    }
}
