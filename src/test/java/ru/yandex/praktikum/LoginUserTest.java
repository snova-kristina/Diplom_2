package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginUserTest extends BaseTestUser{

	@Test
	@DisplayName("Успешный логин под существующим пользователем")
	public void loginExistentUserReturnsOk(){
		userSteps.registerUser(email,password,name);
		ValidatableResponse response = userSteps.loginUser(email,password)
				.statusCode(HttpStatus.SC_OK)
				.body("success", is(true)).and().body("accessToken", notNullValue());
		token = response.extract().path("accessToken");
	}

	@Test
	@DisplayName("Безуспешный логин с неверным логином и паролем")
	public void loginWithIncorrectUserDataReturnsError(){
		String errorMessage = "email or password are incorrect";
		userSteps.registerUser(email,password,name);
		userSteps.loginUser(email,"password")
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				.body("success", is(false)).and().body("message", is(errorMessage));
	}
}
