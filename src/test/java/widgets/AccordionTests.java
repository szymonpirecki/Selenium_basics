package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AccordionTests extends TestBase {
    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/accordion.php");
    }

    @RepeatedTest(1)
    public void shouldPrintContentOfEachSectionTest() throws IOException {
        List<WebElement> sections = driver.findElements(By.cssSelector(".ui-accordion-content"));
        StringBuilder result = new StringBuilder();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Actions actions = new Actions(driver);
        for (WebElement section : sections) {
            if (!section.isDisplayed()) {
                WebElement sectionHeader = section.findElement(By.xpath("preceding-sibling::*[1]"));
                sectionHeader.click();
                wait.until(ExpectedConditions.attributeContains(section, "aria-hidden", "false"));
                actions.scrollByAmount(5, 0).perform();
            }
            String sectionContent = section.getText();
            result.append(sectionContent).append("\n");
        }
        System.out.println(result.toString());
        String actualResult = normalizeWhiteSpaces(result.toString());
        String expectedResult = normalizeWhiteSpaces(loadExpectedResultFromResources("accordionTestExpectedResult"));
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private String loadExpectedResultFromResources(String filename) throws IOException {
        File file = utils.getFileFromResources(filename);
        if (file == null) {
            throw new IllegalArgumentException("File not found");
        }
        return new String(Files.readAllBytes(file.toPath()));
    }

    private String normalizeWhiteSpaces(String stringToNormalize) {
        return stringToNormalize.replaceAll("\\s+", " ").trim();
    }


}
