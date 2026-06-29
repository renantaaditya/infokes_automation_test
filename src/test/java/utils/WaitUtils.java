package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Kumpulan helper untuk explicit wait, dipusatkan di satu tempat
 * agar Page Object tidak duplikasi logic waiting.
 */
public final class WaitUtils {

    public static final int DEFAULT_TIMEOUT_SECONDS = 10;

    private WaitUtils() {
        // utility class, no instantiation
    }

    public static WebDriverWait newWait(WebDriver driver) {
        return newWait(driver, DEFAULT_TIMEOUT_SECONDS);
    }

    public static WebDriverWait newWait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /** Tunggu sampai elemen visible, lalu kembalikan elemennya. */
    public static WebElement waitForVisible(WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Tunggu sampai elemen bisa diklik (visible + enabled), lalu kembalikan elemennya. */
    public static WebElement waitForClickable(WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Tunggu elemen visible tanpa melempar exception ke caller.
     * Berguna untuk assertion "apakah elemen X muncul atau tidak".
     */
    public static boolean waitForVisibleSafe(WebDriverWait wait, By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /** Tunggu elemen sampai hilang/invisible dari DOM atau viewport. */
    public static boolean waitForInvisible(WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Tunggu sampai URL halaman mengandung substring tertentu (contoh: setelah redirect). */
    public static boolean waitForUrlContains(WebDriverWait wait, String fragment) {
        return wait.until(ExpectedConditions.urlContains(fragment));
    }
}
