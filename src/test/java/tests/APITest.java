package tests;

import data.API;
import data.CardGenerator;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class APITest {

    @Test
    void shouldGetStatusValidApprovedCardPayment() {
        val validApprovedCard = CardGenerator.getApprovedCard();
        val status = API.PaymentPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }

    @Test
    void shouldGetStatusValidDeclinedCardPayment() {
        val validDeclinedCard = CardGenerator.getDeclinedCard();
        val status = API.PaymentPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));
    }

    @Test
    void shouldGetStatusValidApprovedCardCreditRequest() {
        val validApprovedCard = CardGenerator.getApprovedCard();
        val status = API.CreditRequestPageForm(validApprovedCard);
        assertTrue(status.contains("APPROVED"));
    }

    @Test
    void shouldGetStatusValidDeclinedCardCreditRequest() {
        val validDeclinedCard = CardGenerator.getDeclinedCard();
        val status = API.CreditRequestPageForm(validDeclinedCard);
        assertTrue(status.contains("DECLINED"));
    }
}
