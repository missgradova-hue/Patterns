package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUp() {
        Configuration.browser = "chrome";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        Configuration.browserCapabilities = options;
    }

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


        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"))
                .shouldHave(text(firstDate));


        $("[data-test-id='date'] input")
                .toWebElement()
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


        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно"))
                .shouldHave(text(secondDate), Duration.ofSeconds(15));
    }
}
//Добрый день.Извиняюсь за доставленные неудобства.Я помню правила сдачи домашних работ.
// Ведь я каждый раз отправляю ссылки на ОБЕ задачи.Не знаю почему так происходит,что приходит какая-то одна.
//Надеюсь в этот раз будут обе.