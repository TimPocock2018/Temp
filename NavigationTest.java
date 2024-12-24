package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class NavigationTest extends BaseTest {

    // Method to wait for an element to be visible on the page
    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }

    @Test
    public void navigationTest() {
        // Perform the valid login steps manually here
        driver.get("https://platform.withintelligence.com/login");
        System.out.println("Navigated to login page.");

        // if the cookie dialog pops up, close it by rejecting the cookies
        WebElement rejectCookiesButton = driver.findElement(By.id("onetrust-reject-all-handler"));
        rejectCookiesButton.click();
        System.out.println("Rejected cookies.");

        // Locate username, password, and login button, and enbter text
        driver.findElement(By.id("login-emailInput")).sendKeys("qa-ui-automation-external@withintelligence.com");
        driver.findElement(By.id("login-password")).sendKeys("Q4tre5!j80");
        driver.findElement(By.id("login-submit")).click();
        System.out.println("Login attempt made.");

        // Wait for the infinite-scroll-component to appear, indicating the page has loaded
        waitForElement(By.className("infinite-scroll-component"));
        System.out.println("Waiting for element to appear.");

        // Now perform the navigation test
        WebElement discoverLink = driver.findElement(By.cssSelector("[data-testid='nav-link-header-discover-link']"));
        discoverLink.click();
        System.out.println("Clicked on Discover link.");

        // Wait for the new page to load
        waitForElement(By.id("searchInputValue"));
        System.out.println("Search input field loaded on Discover page.");

        // Verify the URL after clicking the "Discover" link
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "https://platform.withintelligence.com/all/discover", "Navigation to Explore page failed.");
        System.out.println("Current URL is: " + currentUrl);
    }
}
