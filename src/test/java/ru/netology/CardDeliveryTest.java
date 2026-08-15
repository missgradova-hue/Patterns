package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";
    }

    @AfterAll
    static void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldReplanDelivery() {

        var user = DataGenerator.Registration.generateUser("ru");

        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);

        open("http://localhost:9999");

        // Первая заявка
        $("[data-test-id='city'] input")
                .setValue(user.getCity());

        $("[data-test-id='date'] input")
                .setValue(firstDate);

        $("[data-test-id='name'] input")
                .setValue(user.getName());

        $("[data-test-id='phone'] input")
                .setValue(user.getPhone());

        $("[data-test-id='agreement'] .checkbox__box")
                .click();

        $$("button")
                .findBy(text("Запланировать"))
                .click();

        // Проверяем первую успешную заявку
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15));

        // Меняем только дату
        $("[data-test-id='date'] input")
                .setValue(secondDate);

        $$("button")
                .findBy(text("Запланировать"))
                .click();

        // Проверяем предложение перепланирования
        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));

        // Нажимаем именно "Перепланировать"
        $("[data-test-id='replan-notification']")
                .$$("button")
                .findBy(text("Перепланировать"))
                .click();

        // Проверяем успешное перепланирование
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15));
    }
}