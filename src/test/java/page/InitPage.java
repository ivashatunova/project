package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class InitPage {
    private SelenideElement heading = $(By.cssSelector(".heading_size_l"));
    private SelenideElement paymentButton =  $x("//span[text()='Купить']") ;

    private SelenideElement creditButton = $x("//span[contains(text(), 'кредит')]");

    public InitPage() {
        heading.shouldBe(visible);
    }

    public PaymentPage clickPaymentButton() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage clickCreditButton() {
        creditButton.click();
        return new CreditPage();
    }
}
