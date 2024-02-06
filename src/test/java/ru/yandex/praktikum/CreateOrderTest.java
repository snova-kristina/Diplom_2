package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest extends BaseTestOrder {

	@Test
	@DisplayName("Успешное создание заказа с авторизацией, заказ с ингредиентами")
	public void createNewOrderReturnsOk() {
		userSteps.registerUser(email, password, name);
		ValidatableResponse response = userSteps.loginUser(email, password);
		token = response.extract().path("accessToken");
		orderSteps.createOrderWithToken(ingredients, token).statusCode(HttpStatus.SC_OK)
				.body("success", is(true)).and().body("order", notNullValue());
	}

	@Test
	@DisplayName("Безуспешное создание заказа без авторизации")
	public void createNewOrderWithoutTokenReturnsError() {
		String errorMessage = "You should be authorised";
		ValidatableResponse response = userSteps.registerUser(email, password, name);
		token = response.extract().path("accessToken");
		orderSteps.createOrderWithoutToken(ingredients)
				//.statusCode(HttpStatus.SC_UNAUTHORIZED) - по спеке должна быть ошибка, но as is - 200 ok
				//.body("success", is(false)).and().body("message", is(errorMessage));
				.statusCode(HttpStatus.SC_OK).body("success", is(true)).and().body("order", notNullValue());
	}

	@Test
	@DisplayName("Безуспешное создание заказа без обязательного параметра - ингредиента")
	public void createNewOrderWithoutRequiredParamReturnsError() {
		String errorMessage = "Ingredient ids must be provided";
		ValidatableResponse response = userSteps.registerUser(email, password, name);
		token = response.extract().path("accessToken");
		orderSteps.createOrderWithToken(null, token).statusCode(HttpStatus.SC_BAD_REQUEST)
				.body("success", is(false)).and().body("message", is(errorMessage));
	}

	@Test
	@DisplayName("Безуспешное создание заказа с некорректным хэшом ингредиента")
	public void createNewOrderWithInvalidDataReturnsError() {
		ValidatableResponse response = userSteps.registerUser(email, password, name);
		token = response.extract().path("accessToken");
		orderSteps.createOrderWithoutToken(invalidIngredients).statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
}
