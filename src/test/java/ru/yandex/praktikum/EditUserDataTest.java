package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class EditUserDataTest extends BaseTestUser {

	@Test
	@DisplayName("Успешное изменение данных пользователя - изменение имени")
	public void editUserNameReturnsOk() {
		userSteps.registerUser(email, password, name);
		ValidatableResponse response = userSteps.loginUser(email, password);
		token = response.extract().path("accessToken");
		userSteps.editUserWithToken(email, password, newName, token)
				.statusCode(HttpStatus.SC_OK)
				.body("success", is(true));
	}

	@Test
	@DisplayName("Успешное изменение данных пользователя - изменение пароля")
	public void editUserPasswordReturnsOk() {
		userSteps.registerUser(email, password, name);
		ValidatableResponse response = userSteps.loginUser(email, password);
		token = response.extract().path("accessToken");
		userSteps.editUserWithToken(email, newPassword, name, token)
				.statusCode(HttpStatus.SC_OK)
				.body("success", is(true));
	}

	@Test
	@DisplayName("Успешное изменение данных пользователя - изменение почты")
	public void editUserEmailReturnsOk() {
		userSteps.registerUser(email, password, name);
		ValidatableResponse response = userSteps.loginUser(email, password);
		token = response.extract().path("accessToken");
		userSteps.editUserWithToken(newEmail, password, name, token)
				.statusCode(HttpStatus.SC_OK)
				.body("success", is(true));
	}

	@Test
	@DisplayName("Безуспешное изменение данных пользователя - отсутствует авторизация")
	public void editUserWithoutTokenReturnsError() {
		String errorMessage = "You should be authorised";
		userSteps.registerUser(email, password, name);
		userSteps.editUserWithoutToken(email, password, newName)
				.statusCode(HttpStatus.SC_UNAUTHORIZED)
				.body("success", is(false)).and().body("message", is(errorMessage));
	}
}
