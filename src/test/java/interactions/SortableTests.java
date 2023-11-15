package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testBase.TestBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SortableTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/sortable.php");
    }



    @RepeatedTest(10)
    public void shouldShuffleAndPutBackInOrderTest() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7));
        Collections.shuffle(numbers);
        System.out.println(numbers.toString());
        WebElement sortablePanel = driver.findElement(By.cssSelector("#sortable"));

        for (int i = 0; i < numbers.size(); i++) {
            int numberToOrder = numbers.get(i);
            WebElement elementToMove = sortablePanel.findElement(By.xpath("//li[contains(text(), 'Item " + numberToOrder + "')]"));
            WebElement targetPositionElement = sortablePanel.findElements(By.cssSelector("li")).get(i);

            new Actions(driver).clickAndHold(elementToMove)
                    .moveToElement(targetPositionElement)
                    .release()
                    .perform();
        }

        List<WebElement> sortedListOnWebsite = sortablePanel.findElements(By.cssSelector("li"));
        for (int i = 0; i < numbers.size(); i++) {
            String expectedText = "Item " + numbers.get(i);
            String actualText = sortedListOnWebsite.get(i).getText();
            assertThat(actualText).contains(expectedText);
        }
    }


}

