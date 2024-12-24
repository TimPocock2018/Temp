package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    // Before each test method, set up the WebDriver
    @BeforeMethod
    public void setUp() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\drivers\\chromedriver-win32\\chromedriver.exe");

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");  // Maximize the window on start

        // Initialize the WebDriver with the configured options
        driver = new ChromeDriver(options);

        // Set implicit wait for WebDriver to wait up to 10 seconds for elements to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // After each test method, quit the WebDriver
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Close the browser
        }
    }
}
