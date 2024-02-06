package ru.yandex.praktikum.client;

import io.restassured.response.Response;
import ru.yandex.praktikum.dto.LoginUserRequest;
import ru.yandex.praktikum.dto.RegisterUserRequest;

import static ru.yandex.praktikum.config.Config.*;

public class UserClient extends RestClient {

	public static Response registerUser(RegisterUserRequest registerUserRequest) {
		return getRequestSpecification()
				.body(registerUserRequest)
				.when()
				.post(REGISTER_USER_PATH);
	}
	public static Response loginUser(LoginUserRequest loginUserRequest) {
		return getRequestSpecification()
				.body(loginUserRequest)
				.when()
				.post(LOGIN_USER_PATH);
	}

	public static Response editUserInfoAuthorized(RegisterUserRequest editUserRequest, String token) {
		return getRequestSpecification(token)
				.body(editUserRequest)
				.when()
				.patch(EDIT_USER_PATH);
	}

	public static Response editUserInfoUnauthorized(RegisterUserRequest editUserRequest) {
		return getRequestSpecification()
				.body(editUserRequest)
				.when()
				.patch(EDIT_USER_PATH);
	}

	public static Response deleteUser(String token) {
		return getRequestSpecification(token)
				.when()
				.delete(EDIT_USER_PATH);
	}
}
