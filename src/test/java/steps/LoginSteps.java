package steps;

import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CheckboxPage;
import pages.LoginPage;

import java.time.Duration;

import static org.junit.Assert.*;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private CheckboxPage checkboxPage;

    private WebDriver getDriver() {
        return Hooks.driver;
    }

    @Given("saya membuka halaman login di {string}")
    public void bukaHalamanLogin(String url) {
        loginPage = new LoginPage(getDriver());
        loginPage.openPage(url);
        System.out.println("[WEB] Opened page: " + url);
    }

    @Given("saya membuka halaman {string}")
    public void bukaHalaman(String url) {
        checkboxPage = new CheckboxPage(getDriver());
        checkboxPage.openPage(url);
        System.out.println("[WEB] Opened page: " + url);
    }

    @When("saya memasukkan username {string}")
    public void masukkanUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("saya memasukkan password {string}")
    public void masukkanPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("saya menekan tombol login")
    public void tekanTombolLogin() {
        loginPage.clickLoginButton();
    }

    @Then("halaman berhasil login dan menampilkan pesan sukses")
    public void verifikasiLoginBerhasil() {
        String msg = loginPage.getFlashMessage();
        System.out.println("[WEB] Flash message: " + msg);
        assertTrue("Pesan sukses tidak ditemukan!", msg.contains("You logged into a secure area!"));
    }

    @Then("URL berubah ke halaman secure")
    public void verifikasiUrlSecure() {
        String url = loginPage.getCurrentUrl();
        System.out.println("[WEB] Current URL: " + url);
        assertTrue("URL bukan halaman secure!", url.contains("/secure"));
    }

    @When("saya menekan tombol logout")
    public void tekanTombolLogout() {
        loginPage.clickLogout();
    }

    @Then("saya kembali ke halaman login")
    public void verifikasiKembaliKeLogin() {
        // Tunggu URL benar-benar pindah ke /login sebelum assertion
        int timeout = System.getenv("HEADLESS") != null ? 30 : 10;
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.urlContains("/login"));
        String url = loginPage.getCurrentUrl();
        System.out.println("[WEB] After logout URL: " + url);
        assertTrue("Tidak kembali ke halaman login!", url.contains("/login"));
    }

    @Then("muncul pesan {string}")
    public void verifikasiPesan(String expected) {
        String msg = loginPage.getFlashMessage();
        System.out.println("[WEB] Flash message: " + msg);
        assertTrue("Pesan '" + expected + "' tidak ditemukan!", msg.contains(expected));
    }

    @Then("muncul pesan error login {string}")
    public void verifikasiPesanErrorLogin(String expected) {
        String msg = loginPage.getFlashMessage();
        System.out.println("[WEB] Error message: " + msg);
        assertTrue("Pesan error '" + expected + "' tidak ditemukan dalam: " + msg, msg.contains(expected));
    }

    // Checkbox steps
    @Then("saya memverifikasi halaman checkboxes menampilkan minimal {int} checkbox")
    public void verifikasiJumlahCheckbox(int minCount) {
        int count = checkboxPage.getAllCheckboxes().size();
        System.out.println("[WEB] Checkbox count: " + count);
        assertTrue("Jumlah checkbox kurang dari " + minCount, count >= minCount);
    }

    @When("saya mencentang checkbox pertama")
    public void centangCheckboxPertama() {
        checkboxPage.checkFirstCheckbox();
    }

    @Then("checkbox pertama dalam kondisi tercentang")
    public void verifikasiCheckboxTercentang() {
        assertTrue("Checkbox pertama tidak tercentang!", checkboxPage.isFirstCheckboxChecked());
    }

    // Dropdown steps
    @Then("halaman dropdown berhasil dimuat")
    public void verifikasiDropdownDimuat() {
        assertTrue("Dropdown tidak tampil!", checkboxPage.isDropdownDisplayed());
    }

    @When("saya memilih opsi dropdown {string}")
    public void pilihOpsiDropdown(String option) {
        checkboxPage.selectDropdownByVisibleText(option);
    }

    @Then("opsi {string} terpilih pada dropdown")
    public void verifikasiOpsiTerpilih(String expectedOption) {
        String selected = checkboxPage.getSelectedDropdownOption();
        System.out.println("[WEB] Selected option: " + selected);
        assertEquals("Opsi dropdown tidak sesuai!", expectedOption, selected);
    }
}
