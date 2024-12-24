package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class ModalTest extends BaseTest {

    // Method to wait for an element to be visible on the page
    public void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }

    // Helper method to check if an element is present on the page
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Method to close the feedback modal if it exists
    public void closeModalIfPresent() {
        try {
            // Wait for the modal to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pendo-base")));

            // If modal is present, close it
            WebElement closeButton = driver.findElement(By.id("pendo-close-guide-745ca7ae"));
            closeButton.click();

            // Wait until the modal is no longer visible
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pendo-base")));
            System.out.println("Modal closed successfully (Feedback).");
        } catch (Exception e) {
            // If modal is not present, continue
            System.out.println("No feedback modal appeared.");
        }
    }

    @Test(priority = 1) // Check if modal exists
    public void testModalDoesNotExist() {

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

        // Close the feedback modal if it appears
        closeModalIfPresent();
        
        // Check if the modal is present
        boolean isModalPresent = isElementPresent(By.cssSelector("div#mockModal"));
        if (!isModalPresent) {
            System.out.println("Modal does not exist, as expected.");
        } else {
            System.out.println("Modal exists, but it should not.");
        }

        // Assert the modal does not exist
        Assert.assertFalse(isModalPresent, "The modal should not exist on the page.");
    }

    @Test(priority = 2) //Check if "Go" button is present
    public void testModalCannotBeOpened() {
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

        // Close the feedback modal if it appears
        closeModalIfPresent();       

        // Wait for the page to load
        waitForElement(By.className("infinite-scroll-component"));

        // Navigate to insights page
        driver.get("https://platform.withintelligence.com/all/insights");

        // Check if the "Go" button is present
        boolean isGoButtonPresent = isElementPresent(By.cssSelector("button#openModal"));
        if (!isGoButtonPresent) {
            System.out.println("The 'Go' button is not present, modal does not exist.");
        } else {
            System.out.println("The 'Go' button is present, which is unexpected.");
        }

        // Assert the button is not present
        Assert.assertFalse(isGoButtonPresent, "The 'Go' button should not be present to open the modal.");
    }
}