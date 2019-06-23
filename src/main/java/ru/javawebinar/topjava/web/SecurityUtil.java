package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/*
Класс, из которого приложение будет получать данные авторизированного пользователя.
Находится в пакете web, т.к. авторизация происходит на слое контроллеров и остальные
слои приложения про нее знать не должны
 */
public class SecurityUtil {

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}