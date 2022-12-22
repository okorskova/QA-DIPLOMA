package data;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@NoArgsConstructor
public class CardGenerator {

    @Value
    public static class Card {
        String number;
        String month;
        String year;
        String holder;
        String cvv;
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getPastMonth(int months) {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getPastYear(int years) {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFutureYear(int years) {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getHolder() {
        var faker = new Faker(new Locale("en"));
        var randomLastName = faker.name().lastName();
        var randomFirstName = faker.name().firstName();
        return randomLastName + " " + randomFirstName;
    }

    public static String getCvv() {
        var faker = new Faker();
        return faker.number().digits(3);
    }

    public static Card getApprovedCard() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getDeclinedCard() {
        return new Card(getDeclinedCardNumber(), getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getEmptyCardForm() {
        return new Card("", "", "", "", "");
    }

    public static Card getEmptyCardNumber() {
        return new Card("", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getEmptyCardMonth() {
        return new Card(getApprovedCardNumber(), "", getYear(), getHolder(), getCvv());
    }

    public static Card getEmptyCardYear() {
        return new Card(getApprovedCardNumber(), getMonth(), "", getHolder(), getCvv());
    }

    public static Card getEmptyCardHolder() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "", getCvv());
    }

    public static Card getEmptyCardCVV() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "");
    }

    public static Card getCardNumberLess16Sym() {
        return new Card("444444444444444", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardNumberOver16Sym() {
        return new Card("44444444444444411", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardNumberLetters() {
        return new Card("number", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardNumberSymbols() {
        return new Card("?/@#*-_!(){}[]", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardNumberNulls() {
        return new Card("0000000000000000", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardNumberNotInDatabase() {
        return new Card("4444444444444445", getMonth(), getYear(), getHolder(), getCvv());
    }

    public static Card getCardMonth00() {
        return new Card(getApprovedCardNumber(), "00", getYear(), getHolder(), getCvv());
    }

    public static Card getCardMonthOver12() {
        return new Card(getApprovedCardNumber(), "13", getYear(), getHolder(), getCvv());
    }


    public static Card getCardDateInPast() {
        return new Card(getApprovedCardNumber(), getPastMonth(1), getYear(), getHolder(), getCvv());
    }

    public static Card getCardMonthLetters() {
        return new Card(getApprovedCardNumber(), "month", getYear(), getHolder(), getCvv());
    }

    public static Card getCardMonthSymbols() {
        return new Card(getApprovedCardNumber(), "?/@#*-_!(){}[]", getYear(), getHolder(), getCvv());
    }

    public static Card getCardYear00() {
        return new Card(getApprovedCardNumber(), getMonth(), "00", getHolder(), getCvv());
    }

    public static Card getCardYear1Sym() {
        return new Card(getApprovedCardNumber(), getMonth(), "3", getHolder(), getCvv());
    }

    public static Card getCardYearInPast() {
        return new Card(getApprovedCardNumber(), getMonth(), getPastYear(1), getHolder(), getCvv());
    }

    public static Card getCardDateOverThisYearOn5() {
        return new Card(getApprovedCardNumber(), getMonth(), getFutureYear(6), getHolder(), getCvv());
    }

    public static Card getCardYearLetters() {
        return new Card(getApprovedCardNumber(), getMonth(), "year", getHolder(), getCvv());
    }

    public static Card getCardYearSymbols() {
        return new Card(getApprovedCardNumber(), getMonth(), "?/@#*-_!(){}[]", getHolder(), getCvv());
    }

    public static Card getCardHolderCirillic() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "ПЕТР ИВАНОВ", getCvv());
    }

    public static Card getCardHolder2Sym() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "PE", getCvv());
    }

    public static Card getCardHolder1Word() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "PETR", getCvv());
    }

    public static Card getCardHolderHyphen() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Petr Petrov-Ivanov", getCvv());
    }

    public static Card getCardHolderTooLong() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov Petr Petrov", getCvv());
    }

    public static Card getCardHolderSymbols() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "?/@#*-_!(){}[]", getCvv());
    }

    public static Card getCardHolderNumeric() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), "123456", getCvv());
    }

    public static Card getCardCvv1Sym() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "1");
    }

    public static Card getCardCvv2Sym() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "11");
    }

    public static Card getCardCvvLetters() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "number");
    }

    public static Card getCardCvvSymbols() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "?/@#*-_!(){}[]");
    }

    public static Card getCardCvv000() {
        return new Card(getApprovedCardNumber(), getMonth(), getYear(), getHolder(), "000");
    }
}
