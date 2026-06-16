Feature: DummyAPI User Endpoints
  Sebagai penguji API, saya ingin memverifikasi endpoint User pada DummyAPI
  menggunakan app-id: 63a804408eb0cb069b57e43a

  Background:
    Given header app-id telah disetel dengan nilai "63a804408eb0cb069b57e43a"

  @api @positive
  Scenario: GET List User - Berhasil mendapatkan daftar user
    When saya mengirim GET request ke "/user"
    Then response status code adalah 200
    And response body mengandung field "data"
    And response body mengandung field "total"
    And jumlah data user lebih dari 0

  @api @positive
  Scenario: GET User by ID - Berhasil mendapatkan detail user
    When saya mengirim GET request ke "/user/60d0fe4f5311236168a109ca"
    Then response status code adalah 200
    And response body mengandung field "id"
    And response body mengandung field "firstName"
    And response body mengandung field "lastName"
    And response body mengandung field "email"

  @api @positive
  Scenario: POST Create User - Berhasil membuat user baru
    When saya mengirim POST request ke "/user/create" dengan body:
      """
      {
        "firstName": "Budi",
        "lastName": "Santoso",
        "email": "budi.santoso@example.com"
      }
      """
    Then response status code adalah 200
    And response body mengandung field "id"
    And response body field "firstName" bernilai "Budi"
    And response body field "lastName" bernilai "Santoso"

  @api @positive
  Scenario: GET List User dengan Pagination - Berhasil mengambil halaman kedua
    When saya mengirim GET request ke "/user?page=1&limit=5"
    Then response status code adalah 200
    And response body mengandung field "data"
    And jumlah data dalam list tidak melebihi 5

  @api @positive
  Scenario: PUT Update User - Berhasil mengupdate data user
    Given saya membuat user baru untuk diupdate
    When saya mengirim PUT request ke "/user/{createdUserId}" dengan body:
      """
      {
        "firstName": "UpdatedName"
      }
      """
    Then response status code adalah 200
    And response body field "firstName" bernilai "UpdatedName"

  @api @negative
  Scenario: GET User by ID - Gagal karena ID tidak valid
    When saya mengirim GET request ke "/user/id-tidak-valid-123"
    Then response status code adalah 400
    And response body mengandung field "error"

  @api @negative
  Scenario: POST Create User - Gagal karena tanpa app-id header
    Given header app-id tidak disetel
    When saya mengirim POST request ke "/user/create" dengan body:
      """
      {
        "firstName": "Test",
        "lastName": "User",
        "email": "test@example.com"
      }
      """
    Then response status code adalah 403
    And response body mengandung field "error"

  @api @negative
  Scenario: POST Create User - Gagal karena body tidak lengkap (email kosong)
    When saya mengirim POST request ke "/user/create" dengan body:
      """
      {
        "firstName": "Test",
        "lastName": "Only"
      }
      """
    Then response status code adalah 400
    And response body mengandung field "error"
