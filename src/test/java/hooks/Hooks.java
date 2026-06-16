package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    public static WebDriver driver;

    @Before(value = "@web or @e2e", order = 1)
    public void setUp(Scenario scenario) {
        System.out.println("==============================");
        System.out.println("Starting scenario: " + scenario.getName());
        System.out.println("==============================");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Ganti --headless=new ke --headless biasa, lebih stabil di CI
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        // Tambahan untuk stabilitas di CI
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-setuid-sandbox");
        options.addArguments("--disable-web-security");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        // Implicit wait sebagai jaring pengaman tambahan
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After(value = "@web or @e2e", order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot on Failure - " + scenario.getName());
        }
        System.out.println("==============================");
        System.out.println("Finished scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());
        System.out.println("==============================");
        if (driver != null) {
            driver.quit();
        }
    }
}
