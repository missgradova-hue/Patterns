package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now()
                .plusDays(shift)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(Faker faker) {
        String[] cities = {
                "Москва",
                "Казань",
                "Калуга",
                "Ярославль"
        };

        return faker.options().option(cities);
    }

    public static String generateName(Faker faker) {
        return faker.name().fullName();
    }

    public static String generatePhone(Faker faker) {
        return "+7" + faker.number().digits(10);
    }

    public static class Registration {

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            Faker faker = new Faker(new Locale(locale));

            return new UserInfo(
                    generateCity(faker),
                    generateName(faker),
                    generatePhone(faker)
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}