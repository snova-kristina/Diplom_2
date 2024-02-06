package ru.yandex.praktikum.client;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.praktikum.config.Config.BASE_URI;

public class RestClient {
	protected static RequestSpecification getRequestSpecification(){
		return given()
				.baseUri(BASE_URI)
				.contentType(ContentType.JSON);
	}

	protected static RequestSpecification getRequestSpecification(String token) {
		return given()
				.contentType(ContentType.JSON)
				.header("authorization", token)
				.baseUri(BASE_URI);

	}
}
