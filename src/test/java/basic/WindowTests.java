package basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import testBase.TestBase;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class WindowTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/windows-tabs.php");
    }


    @Test
    public void shouldSwitchToNewWinAndFilterMountainsTest() {
        List<String> expectedResult = List.of("2 Dufourspitze Alps", "3 Dom Alps", "4 Weisshorn Alps", "5 Matterhorn Alps", "6 Finsteaarhorn Alps", "7 Jungfrau Alps");
        String originalWindowHandle = driver.getWindowHandle();
        utils.findElementByCssAndClick("#newBrowserWindow");
        Set<String> windowHandles = driver.getWindowHandles();
        switchToNewWindow(originalWindowHandle, windowHandles);
        List<String> filteredMountains = utils.getPeaksDataBasedOnCountryAndHeight("Switzerland", 4000);
        filteredMountains.forEach(System.out::println);
        closeChildWinAndSwitchToOriginalWin(originalWindowHandle);
        assertThat(filteredMountains).isEqualTo(expectedResult);
    }

    @Test
    public void shouldSwitchToNewWinAndGetContentTest() {
        String expectedBodyContent = "Knowledge increases by sharing but not by saving. Please share this website with your friends and in your organization.";
        String originalWindowHandle = driver.getWindowHandle();
        utils.findElementByCssAndClick("#newMessageWindow");
        Set<String> windowHandles = driver.getWindowHandles();
        switchToNewWindow(originalWindowHandle, windowHandles);
        String bodyContent = driver.findElement(By.cssSelector("body")).getText();
        System.out.println(bodyContent);
        assertThat(bodyContent).isEqualTo(expectedBodyContent);
    }

    @Test
    public void shouldSwitchToNewTabAndFilterMountainsTest() {
        List<String> expectedResult = List.of("2 Dufourspitze Alps", "3 Dom Alps", "4 Weisshorn Alps", "5 Matterhorn Alps", "6 Finsteaarhorn Alps", "7 Jungfrau Alps");
        String originalWindowHandle = driver.getWindowHandle();
        utils.findElementByCssAndClick("#newBrowserTab");
        Set<String> windowHandles = driver.getWindowHandles();
        switchToNewWindow(originalWindowHandle, windowHandles);
        List<String> filteredMountains = utils.getPeaksDataBasedOnCountryAndHeight("Switzerland", 4000);
        filteredMountains.forEach(System.out::println);
        closeChildWinAndSwitchToOriginalWin(originalWindowHandle);
        assertThat(filteredMountains).isEqualTo(expectedResult);
    }

    private void switchToNewWindow(String originWinHandle, Set<String> windowHandles) {
        for (String handle : windowHandles) {
            if (!handle.equals(originWinHandle)) {
                driver.switchTo().window(handle);
            }
        }
    }

    private void closeChildWinAndSwitchToOriginalWin(String originalWinHandle) {
        driver.close();
        driver.switchTo().window(originalWinHandle);
    }
}
