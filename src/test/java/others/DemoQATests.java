package others;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class DemoQATests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("https://demoqa.com/automation-practice-form");
        driver.manage().window().maximize();
    }

    @RepeatedTest(10)
    public void testSubjectSelection() {
        WebElement subjectInput = driver.findElement(By.cssSelector("#subjectsInput"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(subjectInput));
        subjectInput.sendKeys("m");
        WebElement mathsOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#react-select-2-option-0")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", mathsOption);
        mathsOption.click();

        subjectInput.sendKeys("a");
        WebElement artsOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#react-select-2-option-2")));

        js.executeScript("arguments[0].scrollIntoView(true);", artsOption);
        artsOption.click();

        WebElement selectedSubjectsPanel = driver.findElement(By.cssSelector(".subjects-auto-complete__value-container"));
        String selectedSubjectsText = selectedSubjectsPanel.getText();

        assertThat(selectedSubjectsText).contains("Maths", "Arts");
    }
}
