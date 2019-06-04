package main.java.ru.javawebinar.topjava.util;

import main.java.ru.javawebinar.topjava.model.UserMeal;
import main.java.ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        long start1 = System.currentTimeMillis();
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        long fin1 = System.currentTimeMillis();
        System.out.println(fin1 - start1 + " ms");
        System.out.println("***");
        long start2 = System.currentTimeMillis();
        System.out.println(getFilteredWithExceededByStreams(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        long fin2 = System.currentTimeMillis();
        System.out.println(fin2 - start2 + " ms");

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceedsList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayList = new HashMap<>();

        mealList.forEach(userMeal ->
                caloriesPerDayList.merge(userMeal.getDateTime().toLocalDate(), userMeal.getCalories(), Integer::sum));

        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean isExceeded = caloriesPerDayList.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                userMealWithExceedsList.add(createExceeded(userMeal, isExceeded));
            }
        });

        return userMealWithExceedsList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayList =
                mealList.stream()
                        .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal ->
                        createExceeded(meal, caloriesPerDayList.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMealWithExceed createExceeded(UserMeal userMeal, boolean exceed) {
        return new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }
}
