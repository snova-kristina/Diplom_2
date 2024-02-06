package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import ru.yandex.praktikum.client.UserClient;
import ru.yandex.praktikum.step.UserSteps;

import static ru.yandex.praktikum.constants.UserDataConstants.*;

public abstract class BaseTestUser {
	protected UserSteps userSteps;
	protected static String token = null;
	static String email = RANDOM_EMAIL;
	static String newEmail = RANDOM_EMAIL;
	static String password = RANDOM_PASSWORD;
	static String newPassword = RANDOM_PASSWORD;
	static String name = RANDOM_NAME;
	static String newName = RANDOM_NAME;


	@Before
	public void setUp() {
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

