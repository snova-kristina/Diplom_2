package ru.yandex.praktikum.step;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.dto.CreateOrderRequest;

import java.util.List;

public class OrderSteps {
	private final OrderClient orderClient;

	public OrderSteps(OrderClient orderClient) {
		this.orderClient = orderClient;
	}

	@Step("Создание заказа с авторизацией")
	public ValidatableResponse createOrderWithToken(List<String> ingredients, String token) {
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setIngredients(ingredients);
		return OrderClient.createOrderAuthorized(createOrderRequest, token).then().log().all();
	}

	@Step("Создание заказа без авторизации")
	public ValidatableResponse createOrderWithoutToken(List<String> ingredients) {
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setIngredients(ingredients);
		return OrderClient.createOrderUnauthorized(createOrderRequest).then().log().all();
	}

	@Step("Получение заказов авторизованного пользователя")
	public ValidatableResponse getOrdersWithToken(String token) {
		return OrderClient.getUserOrders(token).then().log().all();
	}

	@Step("Получение заказов неавторизованного пользователя")
	public ValidatableResponse getOrdersWithoutToken() {
		return OrderClient.getOrders().then().log().all();
	}
}
