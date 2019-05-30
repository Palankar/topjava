package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        LocalTime thisObjTime;
        LocalDate thisObjDate;
        List<UserMealWithExceed> userMealWithExceedsList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayList = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            thisObjDate = userMeal.getDateTime().toLocalDate();

            if (caloriesPerDayList.containsKey(thisObjDate))
                caloriesPerDayList.replace(thisObjDate, caloriesPerDayList.get(thisObjDate) + userMeal.getCalories());  //прибавляем
            else
                caloriesPerDayList.put(thisObjDate, userMeal.getCalories());    //добавляем
        }

        for (UserMeal userMeal : mealList) {

            if (!caloriesPerDayList.isEmpty()) {
                thisObjTime = userMeal.getDateTime().toLocalTime();

                if (thisObjTime.isAfter(startTime) && thisObjTime.isBefore(endTime)) {

                    if (caloriesPerDayList.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay) {
                        userMealWithExceedsList.add(new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                true
                        ));
                    }
                }
            }
        }

        return userMealWithExceedsList;
    }
}
