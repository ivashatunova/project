package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.CreditPage;
import page.InitPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CreditTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void cleanup() {
        SQLHelper.cleanDatabase();
    }

    private CreditPage makeCreditPayment(DataHelper.PayInfo payInfo) {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        CreditPage creditPage = initPage.clickCreditButton();
        creditPage.creditPay(payInfo);
        return creditPage;
    }

    @Test
    void shouldSuccessfullyCredit() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getApprovedCardInfo());
        creditPage.verifySuccessfulCredit();
    }

    @Test
    void sqlApprovedAfterSuccessfullyCredit() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getApprovedCardInfo());
        creditPage.verifySuccessfulCredit();
        String status = SQLHelper.getCreditPayStatus();
        Assertions.assertEquals(DataHelper.APPROVED_STATUS, status);
    }

    @Test
    void shouldNotCreditDeclinedCard() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getDeclinedCardInfo());
        creditPage.tryToCreditDeclinedCard();
    }

    @Test
        //баг
    void sqlDeclinedWithDeclinedCardCredit() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getDeclinedCardInfo());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String status = SQLHelper.getCreditPayStatus();
        Assertions.assertEquals(DataHelper.DECLINED_STATUS, status);

    }

    @Test
    void shouldNotCreditOldCard() {

        CreditPage creditPage = makeCreditPayment(DataHelper.getOldCardInfo());
        creditPage.tryToCreditOldCard();

    }

    @Test
    void notSqlOldCardCredit() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getOldCardInfo());
        creditPage.tryToCreditOldCard();
        String status = SQLHelper.getCreditPayStatus();
        Assertions.assertNull(status);
    }

    @Test
    void shouldNotCreditInvalidCard() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getInvalidCardInfo());
        creditPage.tryToCreditInvalidCard();
    }

    @Test
    void notSQLInvalidCardCredit() {
        CreditPage creditPage = makeCreditPayment(DataHelper.getInvalidCardInfo());
        creditPage.tryToCreditInvalidCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertNull(status);
    }


}
