package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CreditPage {
    private SelenideElement heading = $x("//h3[contains(text(), 'Кредит по данным карты')] ");
    private SelenideElement cardNumber = $x("//span[contains(text(), 'Номер карты')]");
    private SelenideElement month = $x("//span[contains(text(), 'Месяц')]");
    private SelenideElement year = $x("//span[contains(text(), 'Год')]");
    private SelenideElement user = $x("//span[contains(text(), 'Владелец')]");
    private SelenideElement cvc = $x("//span[contains(text(), 'CVC/CVV')]");
    private SelenideElement continueButton = $x("//button[contains(., 'Продолжить')]");




    public CreditPage() {
        heading.shouldBe(visible);
    }

    public CreditPage creditPay (DataHelper.PayInfo payInfo) {
        cardNumber.setValue(payInfo.getCardNumber());
        month.setValue(payInfo.getMonth());
        year.setValue(payInfo.getYear());
        user.setValue(payInfo.getCardHolder());
        cvc.setValue(payInfo.getCvc());
        continueButton.click();
        return new CreditPage();
    }
}
