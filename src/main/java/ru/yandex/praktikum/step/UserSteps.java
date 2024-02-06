package ru.yandex.praktikum.step;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.dto.LoginUserRequest;
import ru.yandex.praktikum.dto.RegisterUserRequest;

public class UserSteps {
	private final UserClient userClient;

	public UserSteps(UserClient userClient) {
		this.userClient = userClient;
	}

	@Step("Регистрация пользователя")
	public ValidatableResponse registerUser(String email, String password, String name) {
		RegisterUserRequest registerUserRequest = new RegisterUserRequest();
		registerUserRequest.setEmail(email);
		registerUserRequest.setPassword(password);
		registerUserRequest.setName(name);
		return UserClient
				.registerUser(registerUserRequest)
				.then()
				.log().all();
	}

	@Step("Логин пользователя")
	public ValidatableResponse loginUser(String email, String password) {
		LoginUserRequest loginUserRequest = new LoginUserRequest();
		loginUserRequest.setEmail(email);
		loginUserRequest.setPassword(password);
		return UserClient
				.loginUser(loginUserRequest)
				.then()
				.log().all();
	}

	@Step("Обновление данных пользователя с авторизацией")
	public ValidatableResponse editUserWithToken(String email, String password, String name, String token) {
		RegisterUserRequest editUserRequest = new RegisterUserRequest();
		editUserRequest.setEmail(email);
		editUserRequest.setPassword(password);
		editUserRequest.setName(name);
		return UserClient
				.editUserInfoAuthorized(editUserRequest, token)
				.then()
				.log().all();
	}

	@Step("Обновление данных пользователя без авторизации")
	public ValidatableResponse editUserWithoutToken(String email, String password, String name) {
		RegisterUserRequest editUserRequest = new RegisterUserRequest();
		editUserRequest.setEmail(email);
		editUserRequest.setPassword(password);
		editUserRequest.setName(name);
		return UserClient
				.editUserInfoUnauthorized(editUserRequest)
				.then()
				.log().all();
	}

	@Step("Получение токена")
	public String getToken(String email, String password) {
		LoginUserRequest loginUserRequest = new LoginUserRequest();
		loginUserRequest.setEmail(email);
		loginUserRequest.setPassword(password);

		return UserClient.loginUser(loginUserRequest)
				.then()
				.log().all()
				.extract().path("accessToken");
	}

	@Step("Удаление пользователя")
	public void deleteUser(String token) {
		UserClient.deleteUser(token);
	}
}
