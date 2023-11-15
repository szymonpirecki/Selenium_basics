package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testBase.TestBase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ModalDialogTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/modal-dialog.php");
    }

    //    @Test
    @RepeatedTest(10)
    public void shouldCreateNewUserTest() {
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        createNewUser("Jacek", "j.goralski@wp.pl", "jacek123");
        List<WebElement> rowsWithNewUser = driver.findElements(By.cssSelector("tbody tr"));
        assertThat(rows.size() == rowsWithNewUser.size() - 1).isTrue();
    }

    private void createNewUser(String name, String email, String password) {
        utils.findElementByCssAndClick("#create-user");
        driver.findElement(By.cssSelector("#name")).clear();
        utils.sendKeysAndCheckValue("#name", name);
        driver.findElement(By.cssSelector("#email")).clear();
        utils.sendKeysAndCheckValue("#email", email);
        driver.findElement(By.cssSelector("#password")).clear();
        utils.sendKeysAndCheckValue("#password", password);
        utils.findElementByXpathAndClick("//button[contains(text(), \"Create an account\")]");
    }
}
