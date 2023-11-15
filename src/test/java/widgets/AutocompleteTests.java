package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testBase.TestBase;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class AutocompleteTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/autocomplete.php");
    }

    @RepeatedTest(10)
    public void autocompleteTest(){
        WebElement searchInput = driver.findElement(By.cssSelector("#search"));
        searchInput.sendKeys("a");
        WebElement autocompleteTab = driver.findElement(By.cssSelector(".ui-menu"));
        List<WebElement> autocompleteSuggestions = autocompleteTab.findElements(By.cssSelector(".ui-menu-item-wrapper"));
        WebElement randomSuggestion = autocompleteSuggestions.get(new Random().nextInt(autocompleteSuggestions.size()));
        String expectedValue = randomSuggestion.getText();
        randomSuggestion.click();
        assertThat(searchInput.getAttribute("value")).isEqualTo(expectedValue);
    }
}
