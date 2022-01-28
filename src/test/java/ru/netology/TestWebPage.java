package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;


public class TestWebPage {

    private WebDriver driver;
    private ChromeOptions options;

    @BeforeAll
    static void setupAll() {
        //System.setProperty("webdriver.chrome.driver", "./webdriver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void shutdown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testWebPage(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Василий Петров-Иванов");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119418601");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().strip();
        String expectedResult = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedResult, actualResult);
    }


}
