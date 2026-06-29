# Contact Form Automation

Project automation testing untuk form kontak menggunakan **Selenium WebDriver + JUnit 5 + AssertJ**,
disusun dengan pola **Page Object Model (POM)**.

## Struktur Project

```
contact-form-automation/
├── pom.xml
└── src/test/java
     ├── pages/
     │    └── ContactFormPage.java   # Page Object: locator & aksi pada form kontak
     ├── tests/
     │    └── SubmitFormTest.java    # Test case: skenario submit form
     └── utils/
          └── WaitUtils.java         # Helper explicit wait yang dipakai ulang
```

## Requirement

- Java 17+
- Maven 3.8+
- Google Chrome terinstall (driver dikelola otomatis oleh WebDriverManager, jadi tidak perlu
  download `chromedriver` manual)

## Cara Menjalankan

1. Masuk ke folder project:
   ```bash
   cd contact-form-automation
   ```

2. Jalankan semua test:
   ```bash
   mvn clean test
   ```

3. Jalankan dalam mode headless (cocok untuk CI/CD, tanpa membuka jendela browser):
   ```bash
   mvn clean test -Dheadless=true
   ```

4. Jalankan satu test class saja:
   ```bash
   mvn test -Dtest=SubmitFormTest
   ```

## Catatan Implementasi

- **Locator strategy**: semua locator memakai `data-testid` agar tahan terhadap perubahan
  CSS/layout di halaman target. Sesuaikan nilai `data-testid` dan URL (`https://example.com/contact`)
  di `SubmitFormTest.java` dengan target aplikasi yang sebenarnya.
- **Waiting strategy**: tidak ada `Thread.sleep()`. Semua waiting berbasis explicit condition
  (`visibilityOfElementLocated`, `elementToBeClickable`) melalui `WaitUtils`, supaya test stabil
  dan tidak flaky.
- **Driver management**: `WebDriverManager` otomatis mengunduh versi `chromedriver` yang cocok
  dengan versi Chrome yang terinstall di mesin.
- **Test independence**: `@BeforeEach`/`@AfterEach` membuka & menutup browser baru di setiap test,
  jadi test case satu sama lain tidak saling memengaruhi state.

## Pengembangan Selanjutnya (opsional)

- Tambahkan `BasePage` jika nanti ada Page Object lain selain `ContactFormPage`.
- Pindahkan base URL & timeout ke file `config.properties` agar tidak hardcode di test.
- Tambahkan Allure/ExtentReports untuk laporan hasil test yang lebih rapi.
- Tambahkan GitHub Actions workflow (`.github/workflows/test.yml`) untuk menjalankan
  `mvn test -Dheadless=true` otomatis setiap push.
