package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    // Method to wait for an element to be visible on the page
    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
    
    @Test(priority = 1)
    public void testValidSearchQuery() {
        // Perform the valid login steps manually here
        driver.get("https://platform.withintelligence.com/login");
        System.out.println("Navigated to login page.");

        // if the cookie dialog pops up, close it by rejecting the cookies
        WebElement rejectCookiesButton = driver.findElement(By.id("onetrust-reject-all-handler"));
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

        // Start the search test
        driver.findElement(By.id("searchInputValue")).click();
        driver.findElement(By.id("searchInputValue")).sendKeys("credit");

        // Check for the presence of <mark> elements
        List<WebElement> markElements = driver.findElements(By.tagName("mark"));
        Assert.assertTrue(markElements.size() > 0, "No <mark> elements found in the search results.");
        System.out.println("<mark> elements found: " + markElements.size());

        // Print the highlighted text
        for (WebElement mark : markElements) {
            System.out.println("Highlighted text: " + mark.getText());
        }
    }


    @Test(priority = 2)
    public void testNoSearchResults() {
        // Perform the valid login steps manually here
        driver.get("https://platform.withintelligence.com/login");
        System.out.println("Navigated to login page.");

        // if the cookie dialog pops up, close it by rejecting the cookies
        WebElement rejectCookiesButton = driver.findElement(By.id("onetrust-reject-all-handler"));
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

        //Start the search test
        driver.findElement(By.id("searchInputValue")).click();
        driver.findElement(By.id("searchInputValue")).sendKeys("abcdefghihgfedcba");

        // Wait for the result message to appear
        waitForElement(By.cssSelector("p.NotFoundstyled__Text-sc-1v8hwyc-0.fyVxPv"));
        WebElement resultMessage = driver.findElement(By.cssSelector("p.NotFoundstyled__Text-sc-1v8hwyc-0.fyVxPv"));
        String actualText = resultMessage.getText();

        // Expected text
        String expectedText = "Your search for 'abcdefghihgfedcba' did not match any documents.";
        Assert.assertEquals(actualText, expectedText, "The no-results message does not match.");
        System.out.println("Verified no-results message: " + actualText);
    }
}