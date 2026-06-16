package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // The Internet - Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By flashMessage = By.id("flash");
    private By logoutButton = By.cssSelector("a[href='/logout']");

    // SauceDemo - Locators
    private By sauceUsernameField = By.id("user-name");
    private By saucePasswordField = By.id("password");
    private By sauceLoginButton = By.id("login-button");
    private By sauceErrorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        int timeout = System.getenv("HEADLESS") != null ? 30 : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public String getFlashMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(flashMessage)).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void clickLogout() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        // Pakai JS click agar lebih reliable di headless CI
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        // Tunggu URL pindah ke /login setelah logout
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    // SauceDemo methods
    public void enterSauceUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(sauceUsernameField)).sendKeys(username);
    }

    public void enterSaucePassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(saucePasswordField)).sendKeys(password);
    }

    public void clickSauceLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(sauceLoginButton)).click();
    }

    public String getSauceErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(sauceErrorMessage)).getText();
    }
}
