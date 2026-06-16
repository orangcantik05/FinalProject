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
        int timeout = System.getenv("HEADLESS") != null ? 20 : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    // Try-catch dihapus agar TimeoutException langsung terlempar dengan pesan lengkap
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
        System.out.println("[SAUCE] Menunggu tombol add-to-cart muncul...");
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(addToCartButtons));
        System.out.println("[SAUCE] Jumlah tombol add-to-cart: " + buttons.size());
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public String getCartBadgeCount() {
        System.out.println("[SAUCE] Menunggu cart badge muncul...");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public void openCart() {
        System.out.println("[SAUCE] Menunggu cart icon bisa diklik...");
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public int getCartItemCount() {
        System.out.println("[SAUCE] Menunggu item di cart muncul...");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems)).size();
    }

    public void clickCheckout() {
        System.out.println("[SAUCE] Menunggu tombol checkout...");
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public boolean isOnCheckoutInfoPage() {
        try {
            System.out.println("[SAUCE] Menunggu halaman checkout info muncul...");
            boolean result = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).isDisplayed();
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
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public boolean isOnCheckoutOverviewPage() {
        try {
            System.out.println("[SAUCE] Menunggu halaman overview...");
            System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(overviewTitle));
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
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
    }

    public String getThankYouMessage() {
        System.out.println("[SAUCE] Menunggu pesan thank you...");
        System.out.println("[SAUCE] URL saat ini: " + driver.getCurrentUrl());
        return wait.until(ExpectedConditions.visibilityOfElementLocated(thankYouHeader)).getText();
    }
}
