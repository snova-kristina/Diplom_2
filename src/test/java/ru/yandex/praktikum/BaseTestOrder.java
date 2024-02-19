package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.step.OrderSteps;
import ru.yandex.praktikum.step.UserSteps;

import java.util.List;

import static ru.yandex.praktikum.constants.OrderDataConstants.INGREDIENTS_HASH;
import static ru.yandex.praktikum.constants.OrderDataConstants.INVALID_INGREDIENT_HASH;
import static ru.yandex.praktikum.constants.UserDataConstants.*;

public abstract class BaseTestOrder {
	protected OrderSteps orderSteps;
	protected UserSteps userSteps;
	protected List<String> ingredients = List.of(INGREDIENTS_HASH);
	protected List<String> invalidIngredients = List.of(INVALID_INGREDIENT_HASH);
	String email = RANDOM_EMAIL;
	String password = RANDOM_PASSWORD;
	String name = RANDOM_NAME;
	protected String token = null;

	@Before
	public void setUp() {
		OrderClient orderClient = new OrderClient();
		orderSteps = new OrderSteps(orderClient);
		UserClient userClient = new UserClient();
		userSteps = new UserSteps(userClient);
	}

	@After
	public void tearDown() {
		if (token != null) {
			userSteps
					.deleteUser(token);
		}
	}
}
