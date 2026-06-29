package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.ContactFormPage;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitFormTest {

    private WebDriver driver;
    private ContactFormPage formPage;

    @BeforeAll
    static void setUpClass() {
        // Mengunduh & mengelola versi chromedriver yang sesuai secara otomatis
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Jalankan headless di CI dengan: -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.get("https://example.com/contact");
        formPage = new ContactFormPage(driver);
    }

    @Test
    @DisplayName("Submit form dengan data valid harus menampilkan success banner")
    void shouldShowSuccessMessage_whenFormSubmittedWithValidData() {
        // Arrange
        String name = "Budi Santoso";
        String email = "budi.santoso@example.com";
        String message = "Pertanyaan terkait layanan produk.";

        // Act
        formPage.fillForm(name, email, message);
        formPage.submit();

        // Assert — assertion jelas dan deskriptif
        assertThat(formPage.isSubmissionSuccessful())
                .as("Banner sukses seharusnya muncul setelah submit form valid")
                .isTrue();
    }

    @Test
    @DisplayName("Submit form dengan email tidak valid harus menampilkan error")
    void shouldShowError_whenEmailInvalid() {
        formPage.fillForm("Budi Santoso", "invalid-email", "Test message");
        formPage.submit();

        assertThat(formPage.getErrorMessage())
                .as("Pesan error format email seharusnya muncul")
                .contains("format email tidak valid");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
