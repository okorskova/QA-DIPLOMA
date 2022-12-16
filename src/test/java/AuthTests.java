import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTests {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    void setup() {
        open(System.getProperty("sut.url"));
    }

    @AfterEach
    void cleanDataBases() {
        SQL.dropDataBase();
    }

    @Test
    @DisplayName("Успешная оплата картой со статусом APPROVED")
    void shouldSuccessPayIfApprovedCard() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getApprovedCard());
        paymentPage.shouldSuccessNotification();
    }

    @Test
    @DisplayName("Отказ банка в оплате картой со статусом DECLINED")
    void shouldFailurePayIfDeclinedCard() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getDeclinedCard());
        paymentPage.shouldFailureNotification();
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldFailurePayIfEmptyAllFields() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardForm());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Номер карты")
    void shouldFailurePayIfEmptyCardNumber() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardNumber());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Месяц")
    void shouldFailurePayIfEmptyCardMonth() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardMonth());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Год")
    void shouldFailurePayIfEmptyCardYear() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardYear());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Владелец")
    void shouldFailurePayIfEmptyCardHolder() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardHolder());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле CVV")
    void shouldFailurePayIfEmptyCardCVV() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getEmptyCardCVV());
        paymentPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("В поле Номер карты 15 символов")
    void shouldFailurePayIfCardNumberLess16Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberLess16Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Номер карты 17 символов")
    void shouldFailurePayIfCardNumberOver16Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberOver16Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Номер карты заполнено буквами")
    void shouldFailurePayIfCardNumberLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberLetters());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Номер карты заполнено спецсимволами")
    void shouldFailurePayIfCardNumberSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberSymbols());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Номер карты нули")
    void shouldFailurePayIfCardNumberNulls() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberNulls());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Отказ банка в оплате несуществующей картой")
    void shouldFailurePayIfCardNumberNotInDatabase() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardNumberNotInDatabase());
        paymentPage.shouldFailureNotification();
    }

    @Test
    @DisplayName("В поле Месяц нули")
    void shouldFailurePayIfCardMonth00() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardMonth00());
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Месяц 13")
    void shouldFailurePayIfCardMonthOver12() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardMonthOver12());
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("Срок действия карты истек")
    void shouldFailurePayIfCardDateInPast() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardDateInPast());
        paymentPage.shouldExpiredDatePassNotification();
    }

    @Test
    @DisplayName("В поле Месяц буквы")
    void shouldFailurePayIfCardMonthLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardMonthLetters());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Месяц спецсимволы")
    void shouldFailurePayIfCardMonthSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardMonthSymbols());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год нули")
    void shouldFailurePayIfCardYear00() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardYear00());
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Год 1 символ")
    void shouldFailurePayIfCardYear1Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardYear1Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год прошлый год")
    void shouldFailurePayIfCardYearInPast() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardYearInPast());
        paymentPage.shouldExpiredDatePassNotification();
    }

    @Test
    @DisplayName("В поле Год больше, чем срок действия карты")
    void shouldFailurePayIfCardDateOverThisYearOn5() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardDateOverThisYearOn5());
        paymentPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Год буквы")
    void shouldFailurePayIfCardYearLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardYearLetters());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год спецсимволы")
    void shouldFailurePayIfCardYearSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardYearSymbols());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец заполнено кириллицей")
    void shouldFailurePayIfCardHolderCirillic() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolderCirillic());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит 2 символа")
    void shouldFailurePayIfCardHolder2Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolder2Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит одно слово")
    void shouldFailurePayIfCardHolder1Word() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolder1Word());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит слово через дефис")
    void shouldSuccessPayIfCardHolderHyphen() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolderHyphen());
        paymentPage.shouldSuccessNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит слишком длинное название")
    void shouldFailurePayIfCardHolderTooLong() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolderTooLong());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит спецсимволы")
    void shouldFailurePayIfCardHolderSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolderSymbols());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит цифры")
    void shouldFailurePayIfCardHolderNumeric() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardHolderNumeric());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит 1 символ")
    void shouldFailurePayIfCardCvv1Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardCvv1Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит 2 символа")
    void shouldFailurePayIfCardCvv2Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardCvv2Sym());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит буквы")
    void shouldFailurePayIfCardCvvLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardCvvLetters());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит спецсимволы")
    void shouldFailurePayIfCardCvvSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardCvvSymbols());
        paymentPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит нули")
    void shouldFailurePayIfCardCvv000() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCard();
        val paymentPage = new PaymentPage();
        paymentPage.fillCard(CardGenerator.getCardCvv000());
        paymentPage.shouldImproperFormatNotification();
    }

}
