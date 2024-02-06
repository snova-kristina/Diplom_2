package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetUserOrdersTest extends BaseTestOrder {
	@Test
	@DisplayName("Успешное получение заказов пользователя")
	public void getUserOrdersReturnsOk() {
		userSteps.registerUser(email, password, name);
		userSteps.loginUser(email, password);
		token = userSteps.getToken(email, password);
		orderSteps.createOrderWithToken(ingredients, token);
		orderSteps.getOrdersWithToken(token).statusCode(HttpStatus.SC_OK)
				.body("success", is(true)).and().body("orders", notNullValue());
	}

	@Test
	@DisplayName("Безуспешное получение заказов пользователя без авторизации")
	public void getUserOrdersWithoutTokenReturnsError() {
		String errorMessage = "You should be authorised";
		userSteps.registerUser(email, password, name);
		userSteps.loginUser(email, password);
		token = userSteps.getToken(email, password);
		orderSteps.createOrderWithToken(ingredients, token);
		orderSteps.getOrdersWithoutToken().statusCode(HttpStatus.SC_UNAUTHORIZED)
				.body("success", is(false)).and().body("message", is(errorMessage));
	}
}
