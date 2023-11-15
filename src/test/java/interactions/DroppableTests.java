package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testBase.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class DroppableTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/droppable.php");
    }


    @RepeatedTest(10)
    public void shouldDragDropAndGetConfirmationTest(){
        WebElement square = driver.findElement(By.cssSelector("#draggable"));
        WebElement dropZone = driver.findElement(By.cssSelector("#droppable"));
        Actions actions = new Actions(driver);

        actions.dragAndDrop(square, dropZone).perform();
        String dropZoneText = dropZone.findElement(By.cssSelector("p")).getText();
        assertThat(dropZoneText).isEqualTo("Dropped!");


    }
}
