package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class ProgressbarTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/progressbar.php");
    }

    @RepeatedTest(1)
    public void shouldWaitForTextInLabelTest(){
        String expectedValue = "Complete!";
        WebElement progressBarLabel = driver.findElement(By.cssSelector(".progress-label"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".progress-label"), expectedValue));
        assertThat(progressBarLabel.getText()).isEqualTo(expectedValue);
    }

    @RepeatedTest(1)
    public void shouldWaitForTextInClassAtributeTest(){
        String expectedValue = "ui-progressbar-complete";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement progressBarLabel = driver.findElement(By.cssSelector(".ui-progressbar-value"));
        wait.until(ExpectedConditions.attributeContains(By.cssSelector(".ui-progressbar-value"), "class", expectedValue));
        assertThat(progressBarLabel.getAttribute("class")).contains(expectedValue);
    }

}
