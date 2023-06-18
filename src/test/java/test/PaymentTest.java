package test;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.InitPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {


    @Test
    void shouldSuccessfullyPay() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo approvedCardInfo = DataHelper.getApprovedCardInfo();
        PaymentPage paymentPage = initPage.clickPaymentButton();
        paymentPage.pay(approvedCardInfo);
        paymentPage.verifySuccessfulPayment();
    }

    @Test
    void shouldNotPayDeclinedCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo declinedCardInfo = DataHelper.getDeclinedCardInfo();
        PaymentPage paymentPage = initPage.clickPaymentButton();
        paymentPage.pay(declinedCardInfo);
        paymentPage.tryToPayDeclinedCard();
    }

    @Test
    void shouldNotPayOldCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo oldCardInfo = DataHelper.getOldCardInfo();
        PaymentPage paymentPage = initPage.clickPaymentButton();
        paymentPage.pay(oldCardInfo);
        paymentPage.tryToPayOldCard();
    }

    @Test
    void shouldNotPayInvalidCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo invalidCardInfo = DataHelper.getInvalidCardInfo();
        PaymentPage paymentPage = initPage.clickPaymentButton();
        paymentPage.pay(invalidCardInfo);
        paymentPage.tryToPayInvalidCard();
    }

}
