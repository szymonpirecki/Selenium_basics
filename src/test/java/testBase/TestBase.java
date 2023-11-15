package testBase;

import utils.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {
    public WebDriver driver;
    public Utils utils;

    @BeforeEach
    void setDriver() {
//        ChromeOptions opt=new ChromeOptions();
//        opt.addArguments("--remote-allow-origins=*");
//        driver=new ChromeDriver(opt);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        utils = new Utils(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
