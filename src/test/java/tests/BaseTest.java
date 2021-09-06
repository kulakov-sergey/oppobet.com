package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public abstract class BaseTest {

    protected static final String BASE_URL = "https://www.oppabet.com/";

    public BaseTest() {
        Configuration.browser = "chrome";
        Configuration.browserVersion = "93.0";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = Duration.of(1, ChronoUnit.MINUTES).toMillis();
    }

    @BeforeMethod
    public void createNewDriver() {
        open(BASE_URL);
    }

    @AfterMethod
    public void closeDriver() {
        clearBrowserCookies();
        clearBrowserCache();
    }
}
