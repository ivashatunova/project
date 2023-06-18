package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CreditPage {
    private SelenideElement heading = $x("//h3[contains(text(), 'Кредит по данным карты')] ");
    private SelenideElement cardNumber = $x("//span[contains(@class, 'input__inner') and span[contains(text(), 'Номер карты')]]/span[@class='input__box']/input");
    private SelenideElement month = $x("//span[contains(@class, 'input__inner') and span[contains(text(), 'Месяц')]]/span[@class='input__box']/input");
    private SelenideElement year = $x("//span[contains(@class, 'input__inner') and span[contains(text(), 'Год')]]/span[@class='input__box']/input");
    private SelenideElement user = $x("//span[contains(@class, 'input__inner') and span[contains(text(), 'Владелец')]]/span[@class='input__box']/input");
    private SelenideElement cvc = $x("//span[contains(@class, 'input__inner') and span[contains(text(), 'CVC/CVV')]]/span[@class='input__box']/input");
    private SelenideElement continueButton = $x("//button[contains(., 'Продолжить')]");
    private SelenideElement successfulPopup = $x("//div[contains(text(), 'Операция одобрена Банком')]");
    private SelenideElement notSuccessfulPopup = $x("//div[contains(text(), 'Банк отказал')]");
    private SelenideElement errorOldCard = $x("//span [contains(text(), 'Истёк срок')]");
    private SelenideElement errorInvalidCard = $x("//span [contains(text(), 'Истёк срок')]");


    public CreditPage() {
        heading.shouldBe(visible);
    }

    public CreditPage creditPay(DataHelper.PayInfo payInfo) {
        cardNumber.setValue(payInfo.getCardNumber());
        month.setValue(payInfo.getMonth());
        year.setValue(payInfo.getYear());
        user.setValue(payInfo.getCardHolder());
        cvc.setValue(payInfo.getCvc());
        continueButton.click();
        return new CreditPage();
    }

    public void verifySuccessfulCredit() {
        successfulPopup.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void tryToCreditDeclinedCard() {
        notSuccessfulPopup.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void tryToCreditOldCard() {
        errorOldCard.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void tryToCreditInvalidCard() {
        notSuccessfulPopup.shouldBe(visible, Duration.ofSeconds(15));
    }

}
