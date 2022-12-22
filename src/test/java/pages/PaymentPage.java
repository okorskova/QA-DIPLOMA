package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.CardGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    private final SelenideElement number = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("input[placeholder='08']");
    private final SelenideElement year = $("input[placeholder='22']");
    private final SelenideElement holder = $(byText("Владелец")).parent().$("[class=\"input__control\"]");
    private final SelenideElement cvv = $("input[placeholder='999']");

    private final SelenideElement improperFormat =  $(byText("Неверный формат"));
    private final SelenideElement emptyField =  $(byText("Поле обязательно для заполнения"));
    private final SelenideElement invalidExpiredDate =  $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredDatePass =  $(byText("Истёк срок действия карты"));
    private final SelenideElement successNote =  $(byText("Операция одобрена Банком."));
    private final SelenideElement failureNote =  $(byText("Ошибка! Банк отказал в проведении операции."));

    private final SelenideElement continueButton =  $$("button").find(exactText("Продолжить"));

    public void fillCard(CardGenerator.Card card) {
        number.setValue(card.getNumber());
        month.setValue(card.getMonth());
        year.setValue(card.getYear());
        holder.setValue(card.getHolder());
        cvv.setValue(card.getCvv());
        continueButton.click();
    }

    public void shouldImproperFormatNotification() {
        improperFormat.shouldBe(Condition.visible);
    }

    public void shouldEmptyFieldNotification() {
        emptyField.shouldBe(Condition.visible);
    }

    public void shouldInvalidExpiredDateNotification() {
        invalidExpiredDate.shouldBe(Condition.visible);
    }

    public void shouldExpiredDatePassNotification() {
        expiredDatePass.shouldBe(Condition.visible);
    }

    public void shouldSuccessNotification() {
        successNote.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldFailureNotification() {
        failureNote.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

}
