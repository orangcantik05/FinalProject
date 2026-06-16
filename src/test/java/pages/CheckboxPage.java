package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckboxPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By checkboxes = By.cssSelector("input[type='checkbox']");
    private By dropdown = By.id("dropdown");

    public CheckboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public List<WebElement> getAllCheckboxes() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxes));
        return driver.findElements(checkboxes);
    }

    public void checkFirstCheckbox() {
        List<WebElement> boxes = getAllCheckboxes();
        WebElement first = boxes.get(0);
        if (!first.isSelected()) {
            first.click();
        }
    }

    public boolean isFirstCheckboxChecked() {
        return getAllCheckboxes().get(0).isSelected();
    }

    public boolean isDropdownDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown)).isDisplayed();
    }

    public void selectDropdownByVisibleText(String text) {
        WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
        Select select = new Select(dropdownElement);
        select.selectByVisibleText(text);
    }

    public String getSelectedDropdownOption() {
        WebElement dropdownElement = driver.findElement(dropdown);
        Select select = new Select(dropdownElement);
        return select.getFirstSelectedOption().getText();
    }
}
