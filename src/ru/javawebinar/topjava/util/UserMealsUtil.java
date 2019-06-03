package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        LocalDate thisObjDate;
        List<UserMealWithExceed> userMealWithExceedsList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayList = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            thisObjDate = userMeal.getDateTime().toLocalDate();

            caloriesPerDayList.merge(thisObjDate, userMeal.getCalories(), Integer::sum);
        }

        for (UserMeal userMeal : mealList) {

            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {

                if (caloriesPerDayList.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    userMealWithExceedsList.add(new UserMealWithExceed(userMeal, true));
                } else {
                    userMealWithExceedsList.add(new UserMealWithExceed(userMeal, false));
                }
            }
        }

        return userMealWithExceedsList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayList =
                mealList.stream()
                        .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> caloriesPerDayList.get(meal.getDateTime().toLocalDate()) > caloriesPerDay ?
                        new UserMealWithExceed(meal, true) : new UserMealWithExceed(meal, false))
                .collect(Collectors.toList());
    }

}
