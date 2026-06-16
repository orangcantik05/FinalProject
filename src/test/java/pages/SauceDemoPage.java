package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SauceDemoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Inventory / Products page
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

    // Checkout Step 2
    private By overviewTitle = By.className("title");
    private By finishButton = By.id("finish");

    // Checkout Complete
    private By thankYouHeader = By.className("complete-header");

    public SauceDemoPage(WebDriver driver) {
        this.driver = driver;
        int timeout = System.getenv("HEADLESS") != null ? 30 : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public boolean isOnInventoryPage() {
        System.out.println("[SAUCE] Menunggu inventory_container muncul...");
        System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
        boolean result = wait.until(
            ExpectedConditions.visibilityOfElementLocated(inventoryContainer)
        ).isDisplayed();
        System.out.println("[SAUCE] inventory_container ditemukan: " + result);
        return result;
    }

    public void addFirstProductToCart() {
        System.out.println("[SAUCE] Menunggu tombol add-to-cart siap diklik...");
        WebElement firstButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[id^='add-to-cart']")
        ));
        System.out.println("[SAUCE] Tombol add-to-cart siap diklik");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstButton);
        System.out.println("[SAUCE] Tombol add-to-cart sudah diklik");
    }

    public String getCartBadgeCount() {
        System.out.println("[SAUCE] Menunggu cart badge muncul...");
        WebElement badge = wait.until(
            ExpectedConditions.visibilityOfElementLocated(cartBadge)
        );
        String count = badge.getText();
        System.out.println("[SAUCE] Cart badge count: " + count);
        return count;
    }

    public void openCart() {
        System.out.println("[SAUCE] Navigasi langsung ke halaman cart...");
        driver.get("https://www.saucedemo.com/cart.html");
        wait.until(ExpectedConditions.urlContains("/cart.html"));
        System.out.println("[SAUCE] Sudah di halaman cart: " + driver.getCurrentUrl());
    }

    public int getCartItemCount() {
        System.out.println("[SAUCE] Menunggu item di cart muncul...");
        List<WebElement> items = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItems)
        );
        System.out.println("[SAUCE] Jumlah item di cart: " + items.size());
        return items.size();
    }

    public void clickCheckout() {
        System.out.println("[SAUCE] Menunggu tombol checkout...");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isOnCheckoutInfoPage() {
        try {
            System.out.println("[SAUCE] Menunggu halaman checkout info muncul...");
            wait.until(ExpectedConditions.urlContains("/checkout-step-one.html"));
            boolean result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstNameField)
            ).isDisplayed();
            System.out.println("[SAUCE] Halaman checkout info ditemukan: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("[SAUCE] TIMEOUT di isOnCheckoutInfoPage: " + e.getMessage());
            System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void fillCheckoutInfo(String firstName, String lastName, String postalCode) {
        System.out.println("[SAUCE] Mengisi form checkout info...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        System.out.println("[SAUCE] Menunggu tombol Continue...");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isOnCheckoutOverviewPage() {
        try {
            System.out.println("[SAUCE] Menunggu halaman overview...");
            wait.until(ExpectedConditions.urlContains("/checkout-step-two.html"));
            System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
            WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(overviewTitle)
            );
            String text = title.getText();
            System.out.println("[SAUCE] Title halaman overview: " + text);
            return text.equals("Checkout: Overview");
        } catch (Exception e) {
            System.out.println("[SAUCE] TIMEOUT di isOnCheckoutOverviewPage: " + e.getMessage());
            System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void clickFinish() {
        System.out.println("[SAUCE] Menunggu tombol Finish...");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public String getThankYouMessage() {
        System.out.println("[SAUCE] Menunggu pesan thank you...");
        wait.until(ExpectedConditions.urlContains("/checkout-complete.html"));
        System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(thankYouHeader)
        ).getText();
    }
}
