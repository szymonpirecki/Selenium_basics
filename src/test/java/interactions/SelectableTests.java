package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testBase.TestBase;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectableTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/selectable.php");
    }

    @RepeatedTest(10)
    public void shouldSelectOptionAndCheckTest(){
        selectOptionAndCheckIfSelected(new Random().nextInt(6)+1);

    }

    private void selectOptionAndCheckIfSelected(int numberOfItem){
        WebElement selectMenu = driver.findElement(By.cssSelector("#selectable"));
        WebElement optionToSelect = selectMenu.findElement(By.xpath("//li[contains(text(), 'Item " + numberOfItem + "')]"));
        optionToSelect.click();
        WebElement feedbackPanel = driver.findElement(By.cssSelector("#feedback"));
        assertThat(feedbackPanel.getText()).isEqualTo("You've selected: #"+numberOfItem+".");
    }
}
