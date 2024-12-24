package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class SectorNavigationTest extends BaseTest {

    // Method to wait for an element to be visible on the page
    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }

    @Test
    public void sectNavigationTest() {
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

       // Now perform the navigation test
       WebElement dropdown = driver.findElement(By.cssSelector("[id='downshift-0-toggle-button']"));
       dropdown.click(); // Open the dropdown
       System.out.println("Clicked on the dropdown.");

       // Wait for the dropdown options to appear
       waitForElement(By.cssSelector("[id='downshift-0-menu']")); // Ensure the dropdown menu is visible

       // Locate the active descendant
       WebElement activeOption = driver.findElement(By.cssSelector("[id='downshift-0-item-3']"));
       String activeOptionText = activeOption.getText();
       System.out.println("Active option text: " + activeOptionText);

       // Click the "Private Credit" option if it matches the expected text
       if ("Private Credit".equals(activeOptionText)) {
           activeOption.click();
           System.out.println("Selected 'Private Credit' from the dropdown.");
       } else {
           System.out.println("'Private Credit' option not found.");
       }
       waitForElement(By.className("infinite-scroll-component"));

       // Verify the URL after the navigation
       String currentUrl = driver.getCurrentUrl();
       Assert.assertEquals(currentUrl, "https://platform.withintelligence.com/pcfi/insights", "Failed to navigate to Private Credit section.");
       System.out.println("Current URL is: " + currentUrl);
    }
}
