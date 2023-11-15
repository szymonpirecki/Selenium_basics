package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import testBase.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class SliderTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/slider.php");
    }

    @RepeatedTest(10)
    public void sliderTest(){
        moveSliderTo(50);
        checkSliderValue(50);
        moveSliderTo(80);
        checkSliderValue(80);
        moveSliderTo(80);
        checkSliderValue(80);
        moveSliderTo(20);
        checkSliderValue(20);
        moveSliderTo(0);
        checkSliderValue(0);
    }

    private void checkSliderValue(int expectedValue){
        WebElement slider = driver.findElement(By.cssSelector("#custom-handle"));
        int currentValue = Integer.parseInt(slider.getText());
        assertThat(currentValue).isEqualTo(expectedValue);
    }
    private void moveSliderTo(int desiredValue){
        WebElement slider = driver.findElement(By.cssSelector("#custom-handle"));
        int currentValue = Integer.parseInt(slider.getText());
        if (desiredValue == currentValue) return;
        int delta = desiredValue - currentValue;
        Keys keyToSend = delta > 0 ? Keys.ARROW_RIGHT : Keys.ARROW_LEFT;
        for (int i = 0; i < Math.abs(delta) ; i++) {
            slider.sendKeys(keyToSend);
        }
    }

}
