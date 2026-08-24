package ru.netology;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    @Test
    void shouldReplanDelivery() {

        var user = DataGenerator.Registration.generateUser("ru");

        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);

        open("http://localhost:9999");

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


        $("[data-test-id='success-notification'] .notification__content")

                .shouldHave(text("Встреча успешно запланирована на " + firstDate),
                        Duration.ofSeconds(15));


        $("[data-test-id='date'] input")

                .sendKeys(Keys.chord(Keys.CONTROL, "a"),
                        secondDate
                );


        $$("button")
                .findBy(text("Запланировать"))
                .click();


        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));


        $("[data-test-id='replan-notification']")
                .$$("button")
                .findBy(text("Перепланировать"))
                .click();


        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + secondDate),
                        Duration.ofSeconds(15));


    }
}
