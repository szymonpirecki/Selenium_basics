package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testBase.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class TooltipTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/tooltip.php#");
    }

    @RepeatedTest(10)
    public void shouldGetTextFromTooltiptest(){
        WebElement tooltips = driver.findElement(By.xpath("//a[contains(text(), 'Tooltips')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tooltips).perform();
        String tooltipsText = driver.findElement(By.cssSelector(".ui-tooltip-content")).getText();
        System.out.println(tooltipsText);
        assertThat(tooltipsText).isEqualTo("That's what this widget is");
    }
}
