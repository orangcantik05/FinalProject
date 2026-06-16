package steps;

import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.SauceDemoPage;

import static org.junit.Assert.*;

public class CheckoutSteps {

    private WebDriver getDriver() {
        return Hooks.driver;
    }

    private LoginPage loginPage;
    private SauceDemoPage sauceDemoPage;

    @When("saya memasukkan username saucedemo {string}")
    public void masukkanUsernameSauce(String username) {
        loginPage = new LoginPage(getDriver());
        // Inisialisasi sauceDemoPage di sini sekaligus agar tidak null di step berikutnya
        sauceDemoPage = new SauceDemoPage(getDriver());
        loginPage.enterSauceUsername(username);
        System.out.println("[E2E] Entered username: " + username);
    }

    @When("saya memasukkan password saucedemo {string}")
    public void masukkanPasswordSauce(String password) {
        loginPage.enterSaucePassword(password);
    }

    @When("saya menekan tombol login saucedemo")
    public void tekanLoginSauce() {
        loginPage.clickSauceLoginButton();
    }

    @Then("saya berada di halaman produk saucedemo")
    public void verifikasiHalamanProduk() {
        boolean isOnPage = sauceDemoPage.isOnInventoryPage();
        System.out.println("[E2E] On inventory page: " + isOnPage);
        assertTrue("Tidak berhasil login ke halaman produk!", isOnPage);
    }

    @When("saya menambahkan produk pertama ke keranjang")
    public void tambahProdukKeKeranjang() {
        sauceDemoPage.addFirstProductToCart();
        System.out.println("[E2E] Added first product to cart");
    }

    @Then("ikon keranjang menampilkan angka {string}")
    public void verifikasiIkonKeranjang(String expectedCount) {
        String actual = sauceDemoPage.getCartBadgeCount();
        System.out.println("[E2E] Cart badge count: " + actual);
        assertEquals("Jumlah item di keranjang tidak sesuai!", expectedCount, actual);
    }

    @When("saya membuka halaman keranjang")
    public void bukaHalamanKeranjang() {
        sauceDemoPage.openCart();
        System.out.println("[E2E] Opened cart page");
    }

    @Then("halaman keranjang menampilkan {int} produk")
    public void verifikasiJumlahProdukDiKeranjang(int expectedCount) {
        int actual = sauceDemoPage.getCartItemCount();
        System.out.println("[E2E] Cart item count: " + actual);
        assertEquals("Jumlah produk di keranjang tidak sesuai!", expectedCount, actual);
    }

    @When("saya menekan tombol checkout")
    public void tekanTombolCheckout() {
        sauceDemoPage.clickCheckout();
        System.out.println("[E2E] Clicked checkout button");
    }

    @Then("saya berada di halaman informasi checkout")
    public void verifikasiHalamanInfoCheckout() {
        boolean isOn = sauceDemoPage.isOnCheckoutInfoPage();
        System.out.println("[E2E] On checkout info page: " + isOn);
        assertTrue("Tidak berada di halaman informasi checkout!", isOn);
    }

    @When("saya mengisi informasi checkout dengan nama {string} lastname {string} kode pos {string}")
    public void isiInformasiCheckout(String firstName, String lastName, String postalCode) {
        sauceDemoPage.fillCheckoutInfo(firstName, lastName, postalCode);
        System.out.println("[E2E] Filled checkout info: " + firstName + " " + lastName + ", " + postalCode);
    }

    @When("saya menekan tombol Continue")
    public void tekanTombolContinue() {
        sauceDemoPage.clickContinue();
        System.out.println("[E2E] Clicked Continue button");
    }

    @Then("saya berada di halaman overview checkout")
    public void verifikasiHalamanOverviewCheckout() {
        boolean isOn = sauceDemoPage.isOnCheckoutOverviewPage();
        System.out.println("[E2E] On checkout overview page: " + isOn);
        assertTrue("Tidak berada di halaman overview checkout!", isOn);
    }

    @When("saya menekan tombol Finish")
    public void tekanTombolFinish() {
        sauceDemoPage.clickFinish();
        System.out.println("[E2E] Clicked Finish button");
    }

    @Then("muncul pesan pemesanan berhasil {string}")
    public void verifikasiPesanCheckoutBerhasil(String expectedMsg) {
        String actual = sauceDemoPage.getThankYouMessage();
        System.out.println("[E2E] Thank you message: " + actual);
        assertEquals("Pesan checkout berhasil tidak sesuai!", expectedMsg, actual);
    }
}
