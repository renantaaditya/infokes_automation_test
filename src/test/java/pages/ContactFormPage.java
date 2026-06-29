package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

public class ContactFormPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locator strategy: prioritaskan data-testid (stabil terhadap perubahan style/layout)
    private final By nameInput      = By.cssSelector("[data-testid='input-name']");
    private final By emailInput     = By.cssSelector("[data-testid='input-email']");
    private final By messageInput   = By.cssSelector("[data-testid='input-message']");
    private final By submitButton   = By.cssSelector("[data-testid='btn-submit']");
    private final By successBanner  = By.cssSelector("[data-testid='success-banner']");
    private final By errorMessage   = By.cssSelector("[data-testid='error-message']");

    public ContactFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = WaitUtils.newWait(driver, WaitUtils.DEFAULT_TIMEOUT_SECONDS);
    }

    public void fillForm(String name, String email, String message) {
        WebElement name_ = WaitUtils.waitForVisible(wait, nameInput);
        name_.clear();
        name_.sendKeys(name);

        WebElement email_ = WaitUtils.waitForVisible(wait, emailInput);
        email_.clear();
        email_.sendKeys(email);

        WebElement message_ = WaitUtils.waitForVisible(wait, messageInput);
        message_.clear();
        message_.sendKeys(message);
    }

    public void submit() {
        // Waiting berbasis kondisi: tombol harus clickable, bukan sekadar "exist"
        WebElement btn = WaitUtils.waitForClickable(wait, submitButton);
        btn.click();
    }

    public boolean isSubmissionSuccessful() {
        return WaitUtils.waitForVisibleSafe(wait, successBanner);
    }

    public String getErrorMessage() {
        WebElement err = WaitUtils.waitForVisible(wait, errorMessage);
        return err.getText();
    }
}
