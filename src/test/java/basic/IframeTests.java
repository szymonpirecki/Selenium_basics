package basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import testBase.TestBase;

public class IframeTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/iframes.php");
    }

    @RepeatedTest(10)
    public void shouldFillFormsInIframesTest() {
        driver.switchTo().frame("iframe1");
        utils.sendKeysAndCheckValue("#inputFirstName3", "Jacek");
        utils.sendKeysAndCheckValue("#inputSurname3", "Góralski");
        driver.switchTo().defaultContent();
        driver.switchTo().frame("iframe2");
        utils.sendKeysAndCheckValue("#inputLogin", "Jacek");
        utils.sendKeysAndCheckValue("#inputPassword", "jacek123");
        utils.selectRandomOptionFromDropdownAndCheckIfSelected("#inlineFormCustomSelectPref");
        utils.selectRandomRadioButtonAndCheckIfSelected(".form-check-input", 7);
        driver.switchTo().defaultContent();
        utils.findElementByCssAndClick(".nav-ite");
    }
}
