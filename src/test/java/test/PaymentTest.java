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
        paymentPage.verifySuccessfulPayment();
    }

    @Test
    void sqlApprovedAfterSuccessfullyPay() {
        PaymentPage paymentPage = makePayment(DataHelper.getApprovedCardInfo());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String status = SQLHelper.getPayStatus();
        Assertions.assertEquals(DataHelper.APPROVED_STATUS, status);
    }

    @Test
        //баг
    void shouldNotPayDeclinedCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getDeclinedCardInfo());
        paymentPage.tryToPayDeclinedCard();

    }

    @Test
        //баг
    void SqlDeclinedWithDeclinedCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getDeclinedCardInfo());
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String status = SQLHelper.getPayStatus();
        Assertions.assertEquals(DataHelper.DECLINED_STATUS, status);

    }

    @Test
    void shouldNotPayOldCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getOldCardInfo());
        paymentPage.tryToPayOldCard();
    }

    @Test
    void notSqlOldCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getOldCardInfo());
        paymentPage.tryToPayOldCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertNull(status);
    }

    @Test
    void shouldNotPayInvalidCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getInvalidCardInfo());
        paymentPage.tryToPayInvalidCard();
    }

    @Test
    void notSQLInvalidCard() {
        PaymentPage paymentPage = makePayment(DataHelper.getInvalidCardInfo());
        paymentPage.tryToPayInvalidCard();
        String status = SQLHelper.getPayStatus();
        Assertions.assertNull(status);
    }

}
