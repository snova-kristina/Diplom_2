package ru.yandex.praktikum.constants;

import org.apache.commons.lang3.RandomStringUtils;

public class UserDataConstants {

	public static final String RANDOM_EMAIL = RandomStringUtils.randomAlphanumeric(7) + "@yandex.ru";
	public static final String RANDOM_PASSWORD = RandomStringUtils.randomAlphanumeric(10);
	public static final String RANDOM_NAME = RandomStringUtils.randomAlphabetic(8);
}
