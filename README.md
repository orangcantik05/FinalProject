# 🚀 Final Project - Automation Testing Framework

Framework pengujian otomatis lengkap yang mencakup **API Test** dan **Web Test** menggunakan Java, Selenium, Rest Assured, dan Cucumber BDD.

---

## 🧰 Teknologi yang Digunakan

| Teknologi | Versi | Kegunaan |
|-----------|-------|----------|
| Java | 17 | Bahasa Pemrograman |
| Gradle | 8.x | Build Tool |
| Selenium WebDriver | 4.18.1 | Web Automation |
| Rest Assured | 5.4.0 | API Automation |
| Cucumber | 7.15.0 | BDD Framework |
| JUnit | 4.13.2 | Test Runner |
| WebDriverManager | 5.7.0 | Auto Driver Management |
| Masterthought | 5.8.1 | Cucumber Report Generator |
| Chrome (Headless) | Stable | Browser |

---

## 🎯 Cakupan Pengujian

### 🔌 API Test - DummyAPI.io (8 test cases)

| No | Skenario | Tipe | Endpoint |
|----|----------|------|----------|
| 1 | GET List User | ✅ Positive | `GET /user` |
| 2 | GET User by ID | ✅ Positive | `GET /user/:id` |
| 3 | POST Create User | ✅ Positive | `POST /user/create` |
| 4 | GET User dengan Pagination | ✅ Positive | `GET /user?page=1&limit=5` |
| 5 | PUT Update User | ✅ Positive | `PUT /user/:id` |
| 6 | GET User ID tidak valid | ❌ Negative | `GET /user/invalid-id` |
| 7 | POST tanpa app-id header | ❌ Negative | `POST /user/create` |
| 8 | POST body tidak lengkap | ❌ Negative | `POST /user/create` |

**App-ID:** `63a804408eb0cb069b57e43a` (disimpan di header `app-id`)

### 🌐 Web Test - The Internet Herokuapp (6 test cases)

| No | Skenario | Tipe | URL |
|----|----------|------|-----|
| 1 | Login berhasil | ✅ Positive | `/login` |
| 2 | Login kemudian Logout | ✅ Positive | `/login` |
| 3 | Login password salah | ❌ Negative | `/login` |
| 4 | Login username kosong | ❌ Negative | `/login` |
| 5 | Interaksi Checkbox | ✅ Positive | `/checkboxes` |
| 6 | Pilih Opsi Dropdown | ✅ Positive | `/dropdown` |

### 🛒 E2E Test - SauceDemo (1 test case, end-to-end checkout)

| No | Skenario | Tipe | Detail |
|----|----------|------|--------|
| 1 | Login → Pilih Produk → Keranjang → Checkout → Order Berhasil | 🔄 E2E | Full flow checkout |

---

## 📁 Struktur Proyek

```
FinalProject/
│
├── .github/
│   └── workflows/
│       └── automation-pipeline.yml   # CI/CD Pipeline
│
├── src/
│   └── test/
│       ├── java/
│       │   ├── api/
│       │   │   └── ApiContext.java          # Shared API state
│       │   ├── hooks/
│       │   │   └── Hooks.java               # Before/After setup
│       │   ├── pages/
│       │   │   ├── LoginPage.java           # POM - Login
│       │   │   ├── CheckboxPage.java        # POM - Checkbox & Dropdown
│       │   │   └── SauceDemoPage.java       # POM - E2E Checkout
│       │   ├── steps/
│       │   │   ├── ApiSteps.java            # API Step Definitions
│       │   │   ├── LoginSteps.java          # Web Step Definitions
│       │   │   └── CheckoutSteps.java       # E2E Step Definitions
│       │   └── runners/
│       │       ├── RunCucumberTest.java     # All tests runner
│       │       ├── RunApiTest.java          # API tests runner
│       │       ├── RunWebTest.java          # Web tests runner
│       │       └── GenerateCucumberReport.java  # Report generator
│       │
│       └── resources/
│           └── features/
│               ├── api_user.feature         # API Test Scenarios
│               ├── web_login.feature        # Web Test Scenarios
│               └── e2e_checkout.feature     # E2E Checkout Scenario
│
├── build.gradle
├── settings.gradle
└── README.md
```

---

## ▶️ Cara Menjalankan Test

### Menjalankan Semua Test
```bash
./gradlew test
```

### Menjalankan API Test Saja
```bash
./gradlew test --tests "runners.RunApiTest"
```

### Menjalankan Web Test Saja
```bash
./gradlew test --tests "runners.RunWebTest"
```

### Menjalankan berdasarkan Tag
```bash
# API positive tests
./gradlew test -Dcucumber.filter.tags="@api and @positive"

# API negative tests
./gradlew test -Dcucumber.filter.tags="@api and @negative"

# E2E test only
./gradlew test -Dcucumber.filter.tags="@e2e"
```

### Generate Cucumber Report (Masterthought)
```bash
./gradlew generateCucumberReport
```

---

## 📊 Laporan Test (Cucumber Report)

Setelah menjalankan test, laporan tersedia di:

| Format | Lokasi |
|--------|--------|
| HTML (built-in) | `build/reports/cucumber/cucumber-html-report/` |
| JSON | `build/reports/cucumber/cucumber.json` |
| XML (JUnit) | `build/reports/cucumber/junit-report.xml` |
| Masterthought HTML | `build/reports/cucumber/masterthought/` |
| Gradle Test Report | `build/reports/tests/test/index.html` |

Laporan dapat didownload dari tab **Actions → Artifacts** pada GitHub.

---

## 🔄 CI/CD Pipeline (GitHub Actions)

Pipeline terdiri dari 3 jobs:

1. **🔌 API Automation Test** - Menjalankan API tests saja
2. **🌐 Web Automation Test** - Menjalankan Web + E2E tests
3. **🚀 Full Test Suite** - Menjalankan semua test dan generate report

Pipeline berjalan otomatis saat:
- Push ke branch `main` atau `develop`
- Pull Request ke `main`
- Manual trigger (workflow_dispatch)

---

## 🔗 Target URL

| Target | URL |
|--------|-----|
| API | https://dummyapi.io/data/v1/ |
| Web (Login) | https://the-internet.herokuapp.com/login |
| E2E (Checkout) | https://www.saucedemo.com |
