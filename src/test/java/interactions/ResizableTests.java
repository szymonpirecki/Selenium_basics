package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testBase.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ResizableTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/resizable.php");
    }

    @RepeatedTest(1)
    public void shouldResizeWindowTest() {
        resizeWindowAndCheckSize(10, 0);
        resizeWindowAndCheckSize(0, 10);
        resizeWindowAndCheckSize(10, 10);
    }

    private void resizeWindowAndCheckSize(int xOffset, int yOffset){
        WebElement resizableWindow = driver.findElement(By.cssSelector("#resizable"));
        WebElement diagonalResize = resizableWindow.findElement(By.cssSelector(".ui-icon-gripsmall-diagonal-se"));
        Dimension initialWindowSize = resizableWindow.getSize();
        int initialWidth = initialWindowSize.getWidth();
        int initialHigh = initialWindowSize.getHeight();
        Actions actions = new Actions(driver);
        actions.clickAndHold(diagonalResize)
                .moveByOffset(xOffset, yOffset)
                .release()
                .perform();
        Dimension windowSizeAfterAdjustment = resizableWindow.getSize();
        int newWidth = windowSizeAfterAdjustment.getWidth();
        //Asercje nie przechodza, poniewaz to okno po zmianie rozmiarow cofa sie
        assertThat(newWidth).isEqualTo(initialWidth + xOffset);
        assertThat(newWidth).isEqualTo(initialWidth + yOffset);
    }

}
