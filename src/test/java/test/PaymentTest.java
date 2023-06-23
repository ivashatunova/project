package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.InitPage;
import page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
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


    private PaymentPage makePayment(DataHelper.PayInfo payInfo) {
        open("http://localhost:8080");
        InitPage initPage = new InitPage();
        PaymentPage paymentPage = initPage.clickPaymentButton();
        paymentPage.pay(payInfo);
        return paymentPage;
    }

    @Test
    void shouldSuccessfullyPay() {
        PaymentPage paymentPage = makePayment(DataHelper.getApprovedCardInfo());
        paymentPage.verifyApprovedCard();
    }

    @Test
    void sqlApprovedAfterSuccessfullyPay() {
        PaymentPage paymentPage = makePayment(DataHelper.getApprovedCardInfo());
        paymentPage.verifyApprovedCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertEquals(DataHelper.APPROVED_STATUS, status);
    }

    @Test
        //баг
    void shouldNotPayDeclinedCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getDeclinedCardInfo());
        paymentPage.verifyDeclinedCard();
    }

    @Test
        //баг
    void SqlDeclinedWithDeclinedCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getDeclinedCardInfo());
        paymentPage.verifyDeclinedCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertEquals(DataHelper.DECLINED_STATUS, status);
    }

    @Test
    void shouldNotPayOldCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getOldCardInfo());
        paymentPage.verifyOldCard();
    }

    @Test
    void notSqlOldCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getOldCardInfo());
        paymentPage.verifyOldCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertNull(status);
    }

    @Test
    void shouldNotPayInvalidCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getInvalidCardInfo());
        paymentPage.verifyInvalidCard();
    }

    @Test
    void notSQLInvalidCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getInvalidCardInfo());
        paymentPage.verifyInvalidCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertNull(status);
    }

}
