package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class Utils {

    private final WebDriver driver;

    public Utils(WebDriver driver) {
        this.driver = driver;
    }

    public void sendKeysAndCheckValue(String cssSelector, String inputValue) {
        WebElement textInputField = driver.findElement(By.cssSelector(cssSelector));
        textInputField.sendKeys(inputValue);
        assertThat(textInputField.getAttribute("value")).isEqualTo(inputValue);
    }

    public void findElementByCssAndClick(String buttonCssSelector) {
        WebElement button = driver.findElement(By.cssSelector(buttonCssSelector));
        button.click();
    }

    public void findElementByXpathAndClick(String buttonXpathSelector) {
        WebElement button = driver.findElement(By.xpath(buttonXpathSelector));
        button.click();
    }

    public void selectRandomRadioButtonAndCheckIfSelected(String radioButtonsCssSelector, int expetedNumberOfRadioButtons) {
        List<WebElement> radioButtons = driver.findElements(By.cssSelector(radioButtonsCssSelector));
        if (radioButtons.size() != expetedNumberOfRadioButtons)
            fail("There should be " + expetedNumberOfRadioButtons + " options available");
        WebElement randomButton = radioButtons.get(new Random().nextInt(radioButtons.size()));
        randomButton.click();
        assertThat(randomButton.isSelected()).isTrue();
    }

    public void selectRandomOptionFromDropdownAndCheckIfSelected(String dropDownCssSelector) {
        WebElement dropDown = driver.findElement(By.cssSelector(dropDownCssSelector));
        Select select = new Select(dropDown);
        List<WebElement> options = select.getOptions();
        int randomNumber = new Random().nextInt(options.size() - 1) + 1;
        select.selectByIndex(randomNumber);
        String selectedOption = select.getFirstSelectedOption().getText();
        String expectedOption = options.get(randomNumber).getText();
        assertThat(selectedOption).isEqualTo(expectedOption);
    }

    public List<String> getPeaksDataBasedOnCountryAndHeight(String country, int minimalHight) {
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        return rows.stream()
                .filter(r -> r.findElements(By.cssSelector("td")).get(2).getText().contains(country))
                .filter(r -> Integer.parseInt(r.findElements(By.cssSelector("td")).get(3).getText()) >= minimalHight)
                .map(r -> {
                    String rank = r.findElement(By.cssSelector("th")).getText();
                    List<WebElement> columns = r.findElements(By.cssSelector("td"));
                    String peak = columns.get(0).getText();
                    String mountainRange = columns.get(1).getText();
                    return rank + " " + peak + " " + mountainRange;
                })
                .collect(Collectors.toList());
    }

    public File getFileFromResources(String filename) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
    }
}
