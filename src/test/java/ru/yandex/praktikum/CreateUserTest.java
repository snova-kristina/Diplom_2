package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateUserTest extends BaseTestUser {

	@Test
	@DisplayName("Успешное создание уникального пользователя")
	public void createNewUserReturnsOk() {
		userSteps.registerUser(email, password, name)
				.statusCode(HttpStatus.SC_OK)
				.body("success", is(true)).and().body("accessToken", notNullValue());
		token = userSteps.getToken(email, password);
	}

	@Test
	@DisplayName("Безуспешное создание пользователя, который уже существует")
	public void createExistentUserReturnsError() {
		String errorMessage = "User already exists";
		userSteps.registerUser(email, password, name);
		userSteps.registerUser(email, password, name)
				.statusCode(HttpStatus.SC_FORBIDDEN)
				.body("success", is(false)).and().body("message", is(errorMessage));
		token = userSteps.getToken(email, password);
	}

	@Test
	@DisplayName("Безуспешное создание пользователя с отсутствующим обязательным параметром")
	public void createNewUserWithoutRequiredParamReturnsError() {
		String errorMessage = "Email, password and name are required fields";
		userSteps.registerUser(email, password, "")
				.statusCode(HttpStatus.SC_FORBIDDEN)
				.body("success", is(false)).and().body("message", is(errorMessage));
	}
}
