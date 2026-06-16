Feature: E2E Test - Checkout pada SauceDemo
  Sebagai pengguna, saya ingin melakukan proses belanja dari login hingga checkout berhasil
  pada website https://www.saucedemo.com

  @web @e2e
  Scenario: E2E - Login, tambah produk ke keranjang, dan checkout berhasil
    Given saya membuka halaman login di "https://www.saucedemo.com"
    When saya memasukkan username saucedemo "standard_user"
    And saya memasukkan password saucedemo "secret_sauce"
    And saya menekan tombol login saucedemo
    Then saya berada di halaman produk saucedemo

    When saya menambahkan produk pertama ke keranjang
    Then ikon keranjang menampilkan angka "1"

    When saya membuka halaman keranjang
    Then halaman keranjang menampilkan 1 produk

    When saya menekan tombol checkout
    Then saya berada di halaman informasi checkout

    When saya mengisi informasi checkout dengan nama "Budi" lastname "Santoso" kode pos "12345"
    And saya menekan tombol Continue
    Then saya berada di halaman overview checkout

    When saya menekan tombol Finish
    Then muncul pesan pemesanan berhasil "Thank you for your order!"
