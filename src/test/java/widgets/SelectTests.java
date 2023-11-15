package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testBase.TestBase;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/selectmenu.php");
    }

    @RepeatedTest(10)
    public void shouldFillFormTest() {
        selectRandomOptionFromListAndCheck("#speed-button", "#speed-menu", true);
        selectFileByFileNameAndCheck("Some unknown file");
        selectNumberByIndex(new Random().nextInt(19));
        selectRandomOptionFromListAndCheck("#salutation-button", "#salutation-menu", false);
    }

    private void selectNumberByIndex(int index){
        utils.findElementByCssAndClick("#number-button");
        List<WebElement> menuItems = driver.findElement(By.cssSelector("#number-menu")).findElements(By.cssSelector(".ui-menu-item"));
        WebElement elementChosenByIndex = menuItems.get(index);
        String expectedNumber = elementChosenByIndex.getText();
        elementChosenByIndex.click();
        WebElement selectedNumber = driver.findElement(By.cssSelector("#number-button > .ui-selectmenu-text"));
        assertThat(selectedNumber.getText()).isEqualTo(expectedNumber);
    }

    private void selectFileByFileNameAndCheck(String filename) {
        utils.findElementByCssAndClick("#files-button");
        driver.findElement(By.xpath("//div[contains(text(), '" + filename + "')]")).click();
        String selectedFileName = driver.findElement(By.cssSelector("#files-button > .ui-selectmenu-text")).getText();
        assertThat(selectedFileName).isEqualTo(filename);
    }

    private void selectRandomOptionFromListAndCheck(String buttonCssSelector, String menuSelector, boolean isFirstOptionClickable) {
        utils.findElementByCssAndClick(buttonCssSelector);
        List<WebElement> options = driver.findElement(By.cssSelector(menuSelector)).findElements(By.cssSelector(".ui-menu-item"));
        int optionsSize = options.size() - (isFirstOptionClickable ? 0 : 1);
        int randomIndex = new Random().nextInt(optionsSize) + (isFirstOptionClickable ? 0 : 1);
        WebElement randomOption = options.get(randomIndex);
        String expectedText = randomOption.getText();
        randomOption.click();
        assertThat(driver.findElement(By.cssSelector(buttonCssSelector +"> .ui-selectmenu-text")).getText()).isEqualTo(expectedText);
    }
}
