package basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class AlertTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://www.seleniumui.moderntester.pl/alerts.php");
    }

    @RepeatedTest(10)
    public void shouldConfirmAlertPopUpTest() {
        callPopUp("#simple-alert");
        confirmOrDismisPopUp(true);
        checkLabel("#simple-alert-label", "OK button pressed");
    }

    @RepeatedTest(10)
    public void shouldProvideNameAndConfirmAlertPopUpTest() {
        callPopUp("#prompt-alert");
        driver.switchTo().alert().sendKeys("Lord Vader");
        confirmOrDismisPopUp(true);
        checkLabel("#prompt-label", "Hello Lord Vader! How are you today?");
    }

    @RepeatedTest(10)
    public void shouldConfirmAndDismisAlertPopUpTest() {
        callPopUp("#confirm-alert");
        confirmOrDismisPopUp(true);
        checkLabel("#confirm-label", "You pressed OK!");
        callPopUp("#confirm-alert");
        confirmOrDismisPopUp(false);
        checkLabel("#confirm-label", "You pressed Cancel!");
    }

    @RepeatedTest(10)
    public void shouldConfirmDelayedPopUpTest() {
        callPopUp("#delayed-alert");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        confirmOrDismisPopUp(true);
    }


    private void callPopUp(String buttonCssSelector) {
        WebElement button = driver.findElement(By.cssSelector(buttonCssSelector));
        button.click();
    }


    private void confirmOrDismisPopUp(boolean acceptPopUp) {
        if (acceptPopUp)
            driver.switchTo().alert().accept();
        else
            driver.switchTo().alert().dismiss();
    }

    private void checkLabel(String labelCssSelector, String expectedLabel) {
        WebElement label = driver.findElement(By.cssSelector(labelCssSelector));
        assertThat(label.getText()).isEqualTo(expectedLabel);
    }
}
