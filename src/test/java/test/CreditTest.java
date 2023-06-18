package test;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.CreditPage;
import page.InitPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CreditTest {

    @Test
    void shouldSuccessfullyCredit() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo approvedCardInfo = DataHelper.getApprovedCardInfo();
        CreditPage creditPage = initPage.clickCreditButton();
        creditPage.creditPay(approvedCardInfo);
        creditPage.verifySuccessfulCredit();
    }

    @Test
    void shouldNotCreditDeclinedCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo declinedCardInfo = DataHelper.getDeclinedCardInfo();
        CreditPage creditPage = initPage.clickCreditButton();
        creditPage.creditPay(declinedCardInfo);
        creditPage.tryToCreditDeclinedCard();
    }

    @Test
    void shouldNotPayOldCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo oldCardInfo = DataHelper.getOldCardInfo();
        CreditPage creditPage = initPage.clickCreditButton();
        creditPage.creditPay(oldCardInfo);
        creditPage.tryToCreditOldCard();
    }

    @Test
    void shouldNotPayInvalidCard() {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        DataHelper.PayInfo invalidCardInfo = DataHelper.getInvalidCardInfo();
        CreditPage creditPage = initPage.clickCreditButton();
        creditPage.creditPay(invalidCardInfo);
        creditPage.tryToCreditInvalidCard();
    }



}
