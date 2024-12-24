package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest extends BaseTest {

    // Method to wait for an element to be visible on the page
    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test(priority = 1)
    public void testValidLogin() {
        driver.get("https://platform.withintelligence.com/login");
        System.out.println("Navigated to login page.");

        // if the cookie dialog pops up, close it by rejecting the cookies
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement rejectCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler")));
        rejectCookiesButton.click();
        System.out.println("Rejected cookies.");

        // Locate username, password, and login button, and enter text
        driver.findElement(By.id("login-emailInput")).sendKeys("qa-ui-automation-external@withintelligence.com");
        driver.findElement(By.id("login-password")).sendKeys("Q4tre5!j80");
        driver.findElement(By.id("login-submit")).click();
        System.out.println("Login attempt made.");

        // Wait for the infinite-scroll-component to appear, indicating the page has loaded
        waitForElement(By.className("infinite-scroll-component"));
        System.out.println("Waiting for element to appear.");

        // Verify that the current URL is the expected one after successful login
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://platform.withintelligence.com/all/insights", "Login failed with valid credentials.");
        System.out.println("Current URL is: " + currentUrl);
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        driver.get("https://platform.withintelligence.com/login");
        System.out.println("Navigated to login page.");
    
        // if the cookie dialog pops up, close it by rejecting the cookies
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement rejectCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler")));
        rejectCookiesButton.click();
        System.out.println("Rejected cookies.");
        
        // Locate username, password, and login button, and perform actions with invalid credentials
        driver.findElement(By.id("login-emailInput")).sendKeys("invalid@user.com");
        driver.findElement(By.id("login-password")).sendKeys("invalid_pass123fFSEWA");
        driver.findElement(By.id("login-submit")).click();
        System.out.println("Entered invalid credentials.");
    
        // Wait for the error message to be visible
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='login-errorMessage']")));
    
        // Verify that the error message contains the expected text
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue(actualErrorMessage.contains("We didn't recognize the username or password you entered. Please try again or click here to reset your password."), "Error message not displayed correctly. Actual message: " + actualErrorMessage);
        Reporter.log("Error message displayed correctly.", true);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL is: " + currentUrl);
    }
    
}


