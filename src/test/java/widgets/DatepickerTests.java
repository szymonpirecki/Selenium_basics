package widgets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testBase.TestBase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class DatepickerTests extends TestBase {

    @BeforeEach
    public void goToWebsite() {
        driver.get("http://automation-practice.emilos.pl/datepicker.php");
    }


    @RepeatedTest(10)
    public void datepickerTest() {
        LocalDate now = LocalDate.now();
        selectSpecificDate(now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = now.format(formatter);
        checkIfDateIsSelected(formattedDate);

        LocalDate firstDayOfNextMonth = now.plusMonths(1).withDayOfMonth(1);
        selectSpecificDate(firstDayOfNextMonth);
        formattedDate = firstDayOfNextMonth.format(formatter);
        checkIfDateIsSelected(formattedDate);

        LocalDate lastDayOfJanuaryNextYear = LocalDate.of(now.plusYears(1).getYear(), 1, 31);
        formattedDate = lastDayOfJanuaryNextYear.format(formatter);
        selectSpecificDate(lastDayOfJanuaryNextYear);
        checkIfDateIsSelected(formattedDate);

        selectSpecificDate(lastDayOfJanuaryNextYear);
        checkIfDateIsSelected(formattedDate);

        Random random = new Random();
        Month lastMonth = now.minusMonths(1).getMonth();
        int yearOfLastMonth = lastMonth.equals(Month.DECEMBER) ? now.getYear() - 1 : now.getYear();
        int daysInMonth = lastMonth.length(now.isLeapYear());
        LocalDate randomDayFromLastMonth = LocalDate.of(yearOfLastMonth, lastMonth, random.nextInt(daysInMonth) + 1);
        formattedDate = randomDayFromLastMonth.format(formatter);
        selectSpecificDate(randomDayFromLastMonth);
        checkIfDateIsSelected(formattedDate);

        LocalDate startOfLastYear = now.minusYears(1).withDayOfYear(1);
        LocalDate endOfLastYear = startOfLastYear.withDayOfYear(startOfLastYear.lengthOfYear());
        int randomDaysToAdd = random.nextInt(startOfLastYear.lengthOfYear());
        LocalDate randomDateLastYear = startOfLastYear.plusDays(randomDaysToAdd);
        formattedDate = randomDateLastYear.format(formatter);
        selectSpecificDate(randomDateLastYear);
        checkIfDateIsSelected(formattedDate);
    }


    private void checkIfDateIsSelected(String expectedDate) {
        WebElement datepicker = driver.findElement(By.cssSelector("#datepicker"));
        String selectedDate = datepicker.getAttribute("value");
        assertThat(selectedDate).isEqualTo(expectedDate);
    }


    private void selectSpecificDate(LocalDate date) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#datepicker")));
        utils.findElementByCssAndClick("#datepicker");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ui-datepicker")));
        adjustDatePicker(".ui-datepicker-year", text -> Integer.parseInt(text), date.getYear());
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ui-datepicker")));
        adjustDatePicker(".ui-datepicker-month", text -> Month.valueOf(text.toUpperCase()).getValue(), date.getMonthValue());
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ui-datepicker")));
        selectSpecificDay(date.getDayOfMonth());
    }

    private void selectSpecificDay(int dayOfTheMonth) {
        WebElement calendar = driver.findElement(By.cssSelector(".ui-datepicker-calendar"));
        List<WebElement> days = calendar.findElements(By.cssSelector(".ui-state-default:not(.ui-priority-secondary)"));
        days.get(dayOfTheMonth - 1).click();
    }

    private void adjustDatePicker(String datepickerElementSelector, Function<String, Integer> valueExtractor, int desiredValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement datepickerHeader = driver.findElement(By.cssSelector(".ui-datepicker-header"));
        WebElement datepickerElement = datepickerHeader.findElement(By.cssSelector(datepickerElementSelector));
        int currentValue = valueExtractor.apply(datepickerElement.getText());

        if (currentValue == desiredValue) return;

        int monthOffset = (desiredValue - currentValue) > 0 ? 1 : -1;
        while (currentValue != desiredValue) {
            changeCalendarPage(monthOffset);
            datepickerElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(datepickerElementSelector)));
            currentValue = valueExtractor.apply(datepickerElement.getText());
        }
    }

    private void changeCalendarPage(int monthOffset) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (monthOffset == 0) return;
        String buttonCssSelector = monthOffset < 0 ? ".ui-datepicker-prev" : ".ui-datepicker-next";
        for (int i = 0; i < Math.abs(monthOffset); i++) {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(buttonCssSelector)));
            button.click();
        }
    }
}
