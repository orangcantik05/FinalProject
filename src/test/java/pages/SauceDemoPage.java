package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SauceDemoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Inventory / Products page -- diganti ke id yang unik
    private By inventoryContainer = By.id("inventory_container");
    private By addToCartButtons = By.cssSelector("button[id^='add-to-cart']");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartIcon = By.id("shopping_cart_container");

    // Cart page
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");

    // Checkout Step 1
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");

    // Checkout Step 2 -- tetap pakai className title karena hanya dipakai untuk teks saja
    private By overviewTitle = By.className("title");
    private By finishButton = By.id("finish");

    // Checkout Complete
    private By thankYouHeader = By.className("complete-header");

    public SauceDemoPage(WebDriver driver) {
        this.driver = driver;
        // Timeout 20 detik saat headless CI, 10 detik saat lokal
        int timeout = System.getenv("HEADLESS") != null ? 20 : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    // Diperbaiki -- pakai inventoryContainer yang unik, bukan className "title"
    public boolean isOnInventoryPage() {
        try {
            return wait.until(
                ExpectedConditions.visibilityOfElementLocated(inventoryContainer)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void addFirstProductToCart() {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(addToCartButtons));
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public int getCartItemCount() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems)).size();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public boolean isOnCheckoutInfoPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public boolean isOnCheckoutOverviewPage() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(overviewTitle));
            return title.getText().equals("Checkout: Overview");
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public String getThankYouMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(thankYouHeader)).getText();
    }
}
