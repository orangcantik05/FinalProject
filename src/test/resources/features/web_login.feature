Feature: Web Automation - The Internet Herokuapp
  Sebagai penguji web, saya ingin memverifikasi fungsionalitas website
  https://the-internet.herokuapp.com

  @web @positive
  Scenario: Login berhasil dengan kredensial valid
    Given saya membuka halaman login di "https://the-internet.herokuapp.com/login"
    When saya memasukkan username "tomsmith"
    And saya memasukkan password "SuperSecretPassword!"
    And saya menekan tombol login
    Then halaman berhasil login dan menampilkan pesan sukses
    And URL berubah ke halaman secure

  @web @positive
  Scenario: Login berhasil kemudian logout
    Given saya membuka halaman login di "https://the-internet.herokuapp.com/login"
    When saya memasukkan username "tomsmith"
    And saya memasukkan password "SuperSecretPassword!"
    And saya menekan tombol login
    Then halaman berhasil login dan menampilkan pesan sukses
    When saya menekan tombol logout
    Then saya kembali ke halaman login
    And muncul pesan "You logged out of the secure area!"

  @web @negative
  Scenario: Login gagal dengan password salah
    Given saya membuka halaman login di "https://the-internet.herokuapp.com/login"
    When saya memasukkan username "tomsmith"
    And saya memasukkan password "passwordsalah"
    And saya menekan tombol login
    Then muncul pesan error login "Your password is invalid!"

  @web @negative
  Scenario: Login gagal dengan username kosong
    Given saya membuka halaman login di "https://the-internet.herokuapp.com/login"
    When saya memasukkan username ""
    And saya memasukkan password "SuperSecretPassword!"
    And saya menekan tombol login
    Then muncul pesan error login "Your username is invalid!"

  @web @positive
  Scenario: Verifikasi halaman Checkboxes dapat diinteraksi
    Given saya membuka halaman "https://the-internet.herokuapp.com/checkboxes"
    Then saya memverifikasi halaman checkboxes menampilkan minimal 2 checkbox
    When saya mencentang checkbox pertama
    Then checkbox pertama dalam kondisi tercentang

  @web @positive
  Scenario: Verifikasi halaman Dropdown dapat memilih opsi
    Given saya membuka halaman "https://the-internet.herokuapp.com/dropdown"
    Then halaman dropdown berhasil dimuat
    When saya memilih opsi dropdown "Option 1"
    Then opsi "Option 1" terpilih pada dropdown
