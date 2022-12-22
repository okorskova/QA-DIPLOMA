package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private static final SelenideElement heading = $$("h2").find(exactText("Путешествие дня"));
    private static final SelenideElement payWithCardButton = $$("button").find(exactText("Купить"));
    private static final SelenideElement payWithCreditButton = $$("button").find(exactText("Купить в кредит"));

    public MainPage() {
        heading.shouldBe(visible);
    }
    public void payWithCard() {
        payWithCardButton.shouldBe(Condition.visible).click();
    }

    public void payWithCredit() {
        payWithCreditButton.shouldBe(Condition.visible).click();
    }
}
