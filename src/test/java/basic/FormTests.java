package basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import testBase.TestBase;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FormTests extends TestBase {


    @BeforeEach
    public void goToWebsite() {
        driver.get("http://www.seleniumui.moderntester.pl/form.php");
    }

    @RepeatedTest(10)
    public void shouldFillFormAndSignInTest() {
        utils.sendKeysAndCheckValue("#inputFirstName3", "Jacek");
        utils.sendKeysAndCheckValue("#inputLastName3", "Góralski");
        utils.sendKeysAndCheckValue("#inputEmail3", "jacek.goralski@gmail.com");
        utils.selectRandomRadioButtonAndCheckIfSelected("input[name=gridRadiosSex]", 3);
        utils.sendKeysAndCheckValue("#inputAge3", "31");
        utils.selectRandomRadioButtonAndCheckIfSelected("input[name='gridRadiosExperience']", 7);
        selectSpecificCheckboxAndCheckIfSelected("#gridCheckAutomationTester");
        utils.selectRandomOptionFromDropdownAndCheckIfSelected("#selectContinents");
        selectMultipleOptionsAndCheckIfSelected("#selectSeleniumCommands", List.of("switch-commands", "wait-commands"));
        uploadFileAndCheckIfUploaded("#chooseFile", "label[for='chooseFile']", utils.getFileFromResources("jacek_goralski.jpg"));
        utils.findElementByCssAndClick("button[type='submit']");
        WebElement finalMessage = driver.findElement(By.cssSelector("#validator-message"));
        assertThat(finalMessage.getText()).isEqualTo("Form send with success");
    }


    private void selectSpecificCheckboxAndCheckIfSelected(String checkboxCssSelector) {
        WebElement checkbox = driver.findElement(By.cssSelector(checkboxCssSelector));
        checkbox.click();
        assertThat(checkbox.isSelected()).isTrue();
    }

    private void selectMultipleOptionsAndCheckIfSelected(String selectCssSelector, List<String> valuesOfOptionsToSelect) {
        WebElement multipleSelect = driver.findElement(By.cssSelector(selectCssSelector));
        Select select = new Select(multipleSelect);
        if (!select.isMultiple())
            fail("The dropdown should allow multiple selections.");
        for (String optionToSelect : valuesOfOptionsToSelect) {
            select.selectByValue(optionToSelect);
        }
        List<WebElement> allSelectedOptions = select.getAllSelectedOptions();
        assertThat(allSelectedOptions.stream()
                .map(o -> o.getAttribute("value"))
                .collect(Collectors.toList()))
                .containsExactlyInAnyOrderElementsOf(valuesOfOptionsToSelect)
                .hasSize(valuesOfOptionsToSelect.size());
    }

    private void uploadFileAndCheckIfUploaded(String uploadFileInputCssSelector, String fileInputLabelCssSelector, File file) {
        String absolutePath = file.getAbsolutePath();
        WebElement fileInput = driver.findElement(By.cssSelector(uploadFileInputCssSelector));
        fileInput.sendKeys(absolutePath);
        WebElement fileInputLabel = driver.findElement(By.cssSelector(fileInputLabelCssSelector));
        assertThat(fileInputLabel.getText()).contains(file.getName());
    }
}
