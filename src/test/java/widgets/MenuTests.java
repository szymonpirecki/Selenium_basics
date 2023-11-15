package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.time.Duration;

public class MenuTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/menu-item.php");
    }

    @Test
    public void shouldGoThroughMenuTreeToModernJazzTest() throws InterruptedException {
        String musicOptionXpath = "//div[contains(text(), \"Music\")]";
        String jazzOptionXpath = "//div[contains(text(), \"Music\")]";
        String modernOptionXpath = "//div[contains(text(), \"Music\")]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        utils.findElementByXpathAndClick(musicOptionXpath);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(jazzOptionXpath)));
        utils.findElementByXpathAndClick(jazzOptionXpath);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(modernOptionXpath)));
        utils.findElementByXpathAndClick(modernOptionXpath);
    }
}
