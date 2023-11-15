package interactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import testBase.TestBase;

public class DraggableTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/draggable.php");
        driver.manage().window().maximize();
    }


    @RepeatedTest(10)
    public void shouldDragSquareToTargetTest(){
        dragSquareToTarget("right-up", "#draggable");
        dragSquareToTarget("right-down", "#draggable");
        dragSquareToTarget("center", "#draggable");
        dragSquareToTarget("left-down", "#draggable");
    }
    private void dragSquareToTarget(String target, String squareCssSelector) {
        WebElement square = driver.findElement(By.cssSelector(squareCssSelector));
        Actions actions = new Actions(driver);
        Dimension squareSize = square.getSize();
        Point squareLocation = square.getLocation();
        Dimension windowSize = driver.manage().window().getSize();
        int xOffset = 0;
        int yOffset = -0;

        switch (target) {
            case "right-up":
                xOffset = windowSize.getWidth() - squareLocation.getX() - squareSize.getWidth() - squareSize.getHeight();
                yOffset = -squareLocation.getY();
                break;
            case "right-down":
                xOffset = windowSize.getWidth() - squareLocation.getX() - squareSize.getWidth() - squareSize.getHeight();
                yOffset = windowSize.getHeight() - squareLocation.getY() - squareSize.getHeight() - squareSize.getWidth();
                break;
            case "center":
                xOffset = (windowSize.getWidth() / 2) - (squareLocation.getX() + squareSize.getWidth() / 2 + squareSize.getHeight() / 2);
                yOffset = (windowSize.getHeight() / 2) - (squareLocation.getY() + squareSize.getHeight() / 2 + squareSize.getWidth() / 2);
                break;
            case "left-down":
                xOffset = -squareLocation.getX();
                yOffset = windowSize.getHeight() - squareLocation.getY() - squareSize.getHeight() - squareSize.getWidth();
                break;
        }
        actions.dragAndDropBy(square, xOffset, yOffset).perform();
    }
}
