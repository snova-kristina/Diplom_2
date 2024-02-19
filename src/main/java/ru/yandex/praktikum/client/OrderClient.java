package ru.yandex.praktikum.client;

import io.restassured.response.Response;
import ru.yandex.praktikum.dto.CreateOrderRequest;

import static ru.yandex.praktikum.config.Config.ORDER_PATH;

public class OrderClient extends RestClient {
	public static Response createOrderAuthorized(CreateOrderRequest createOrderRequest, String token) {
		return getRequestSpecification(token)
				.body(createOrderRequest)
				.when()
				.post(ORDER_PATH);
	}

	public static Response createOrderUnauthorized(CreateOrderRequest createOrderRequest) {
		return getRequestSpecification()
				.body(createOrderRequest)
				.when()
				.post(ORDER_PATH);
	}

	public static Response getUserOrders(String token) {
		return getRequestSpecification(token)
				.when()
				.get(ORDER_PATH);
	}

	public static Response getOrders() {
		return getRequestSpecification()
				.when()
				.get(ORDER_PATH);
	}
}
