package others;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import testBase.TestBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class HighSiteTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/high-site.php");
    }

    @RepeatedTest(1)
    public void shouldScrollToButtonUsingActionAndTakeScreenShotTest() {
        WebElement button = scrollToButtonWithActions(By.cssSelector("#scroll-button"));
        checkButtonValue(button, "Submit");
        takeAScreenShot();
    }

    @RepeatedTest(10)
    public void shouldScrollToButtonUsingJSAndTakeScreenShotTest() {
        WebElement button = scrollToButtonWithJS(By.cssSelector("#scroll-button"));
        checkButtonValue(button, "Submit");
        takeAScreenShot();
    }

    private WebElement scrollToButtonWithJS(By buttonSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement button = null;
        int pageHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        int scrolled = 0;
        int scrollAmount = 100;
        int maxScrollAttempts = pageHeight / scrollAmount;
        int attempts = 0;

        while (button == null && attempts < maxScrollAttempts) {
            try {
                js.executeScript("window.scrollBy(0, arguments[0])", scrollAmount);
                scrolled += scrollAmount;
                button = driver.findElement(buttonSelector);
            } catch (NoSuchElementException e) {
                attempts++;
            }
        }
        return button;
    }

    private WebElement scrollToButtonWithActions(By buttonSelector) {
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int pageHeight = ((Number) js.executeScript("return document.body.scrollHeight")).intValue();
        int maxScrollAttempts = pageHeight / 100;
        WebElement button = null;
        int attempts = 0;

        while (button == null && attempts < maxScrollAttempts) {
            try {
                actions.scrollByAmount(0, 100).perform();
                button = driver.findElement(buttonSelector);
            } catch (NoSuchElementException e) {
                attempts++;
            }
        }
        return button;
    }

    private void checkButtonValue(WebElement button, String expectedButtonValue) {
        assertThat(button.getAttribute("value")).isEqualTo(expectedButtonValue);
    }

    private void takeAScreenShot(){
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        File screenshotFile = screenshotDriver.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String dateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = "shouldScrollToButtonAndTakeScreenShotTest_" + timestamp + ".png";
        File directory = new File("src/test/java/screenshots/" + dateFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File destinationFile = new File(directory, fileName);
        try {
            FileHandler.copy(screenshotFile, destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}