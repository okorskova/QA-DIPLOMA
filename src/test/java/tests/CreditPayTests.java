package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.CardGenerator;
import data.SQL;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.*;
import pages.CreditPayPage;
import pages.MainPage;
import pages.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static data.SQL.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPayTests {

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
        SQL.cleanDatabase();
    }

    @SneakyThrows
    @Test
    @DisplayName("Успешная оплата кредитной картой со статусом APPROVED")
    void shouldSuccessPayIfApprovedCard() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getApprovedCard());
        creditPayPage.shouldSuccessNotification();
        assertEquals("APPROVED", getCardStatusForCreditRequest());
    }

    @Test
    @DisplayName("Отказ банка в оплате кредитной картой со статусом DECLINED")
    void shouldFailurePayIfDeclinedCard() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getDeclinedCard());
        creditPayPage.shouldFailureNotification();
        assertEquals("DECLINED", getCardStatusForCreditRequest());
    }

    @Test
    @DisplayName("Отправка пустой формы")
    void shouldFailurePayIfEmptyAllFields() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardForm());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Номер карты")
    void shouldFailurePayIfEmptyCardNumber() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardNumber());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Месяц")
    void shouldFailurePayIfEmptyCardMonth() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardMonth());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Год")
    void shouldFailurePayIfEmptyCardYear() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardYear());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле Владелец")
    void shouldFailurePayIfEmptyCardHolder() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardHolder());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("Пустое поле CVV")
    void shouldFailurePayIfEmptyCardCVV() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getEmptyCardCVV());
        creditPayPage.shouldEmptyFieldNotification();
    }

    @Test
    @DisplayName("В поле Номер карты 15 символов")
    void shouldFailurePayIfCardNumberLess16Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberLess16Sym());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Номер карты 17 символов")
    void shouldFailurePayIfCardNumberOver16Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberOver16Sym());
        creditPayPage.shouldSuccessNotification();
    }

    @Test
    @DisplayName("Поле Номер карты заполнено буквами")
    void shouldFailurePayIfCardNumberLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberLetters());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Номер карты заполнено спецсимволами")
    void shouldFailurePayIfCardNumberSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberSymbols());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Номер карты нули")
    void shouldFailurePayIfCardNumberNulls() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberNulls());
        creditPayPage.shouldFailureNotification();
    }

    @Test
    @DisplayName("Отказ банка в оплате несуществующей картой")
    void shouldFailurePayIfCardNumberNotInDatabase() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardNumberNotInDatabase());
        creditPayPage.shouldFailureNotification();
    }

    @Test
    @DisplayName("В поле Месяц нули")
    void shouldFailurePayIfCardMonth00() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardMonth00());
        creditPayPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Месяц 13")
    void shouldFailurePayIfCardMonthOver12() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardMonthOver12());
        creditPayPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Месяц буквы")
    void shouldFailurePayIfCardMonthLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardMonthLetters());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Месяц спецсимволы")
    void shouldFailurePayIfCardMonthSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardMonthSymbols());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год нули")
    void shouldFailurePayIfCardYear00() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardYear00());
        creditPayPage.shouldExpiredDatePassNotification();
    }

    @Test
    @DisplayName("В поле Год 1 символ")
    void shouldFailurePayIfCardYear1Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardYear1Sym());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год прошлый год")
    void shouldFailurePayIfCardYearInPast() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardYearInPast());
        creditPayPage.shouldExpiredDatePassNotification();
    }

    @Test
    @DisplayName("В поле Год больше, чем срок действия карты")
    void shouldFailurePayIfCardDateOverThisYearOn5() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardDateOverThisYearOn5());
        creditPayPage.shouldInvalidExpiredDateNotification();
    }

    @Test
    @DisplayName("В поле Год буквы")
    void shouldFailurePayIfCardYearLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardYearLetters());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("В поле Год спецсимволы")
    void shouldFailurePayIfCardYearSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardYearSymbols());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец заполнено кириллицей")
    void shouldFailurePayIfCardHolderCirillic() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolderCirillic());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит 2 символа")
    void shouldFailurePayIfCardHolder2Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolder2Sym());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит одно слово")
    void shouldFailurePayIfCardHolder1Word() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolder1Word());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит слово через дефис")
    void shouldSuccessPayIfCardHolderHyphen() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolderHyphen());
        creditPayPage.shouldSuccessNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит слишком длинное название")
    void shouldFailurePayIfCardHolderTooLong() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolderTooLong());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит спецсимволы")
    void shouldFailurePayIfCardHolderSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolderSymbols());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле Владелец содержит цифры")
    void shouldFailurePayIfCardHolderNumeric() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardHolderNumeric());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит 1 символ")
    void shouldFailurePayIfCardCvv1Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardCvv1Sym());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит 2 символа")
    void shouldFailurePayIfCardCvv2Sym() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardCvv2Sym());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит буквы")
    void shouldFailurePayIfCardCvvLetters() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardCvvLetters());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит спецсимволы")
    void shouldFailurePayIfCardCvvSymbols() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardCvvSymbols());
        creditPayPage.shouldImproperFormatNotification();
    }

    @Test
    @DisplayName("Поле CVV содержит нули")
    void shouldSuccessPayIfCardCvv000() {
        MainPage mainPage = new MainPage();
        mainPage.payWithCredit();
        val creditPayPage = new CreditPayPage();
        creditPayPage.fillCard(CardGenerator.getCardCvv000());
        creditPayPage.shouldSuccessNotification();
    }
}
