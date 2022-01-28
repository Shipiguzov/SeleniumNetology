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

    //Позитивные проверки (Поле "Имя и фамилия" содержат дефис и пробелы)

    @Test
    void rightTest(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Василий Петров-Иванов");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119418601");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector("[data-test-id=\"order-success\"]")).getText().strip();
        String expectedResult = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    //Негативные проверки

    //Имя и Фамилия содержат латинские символы
    @Test
    void invalidNameField(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("someName");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119418601");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector(".input_invalid")).getText();
        //String expectedResult = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertNotNull(actualResult);
    }

    //Phone number have less then 10 numbers
    @Test
    void invalidPhoneField(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Василий Петров-Иванов");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79119");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector(".input_invalid")).getText();
        Assertions.assertNotNull(actualResult);
    }

    //Phone number have more 10 numbers
    @Test
    void phoneNumberMore(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("as");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+123456789012");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector(".input_invalid")).getText();
        Assertions.assertNotNull(actualResult);
    }

    //Checkbox not checked
    @Test
    void uncheckedCheck(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("Василий Петров-Иванов");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+79012345678");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector(".input_invalid")).getText();
        Assertions.assertNotNull(actualResult);
    }

    //invalid Name and Phone fields
    @Test
    void invalidNameAndPhone(){
        driver.findElement(By.cssSelector("[type=\"text\"]")).sendKeys("as");
        driver.findElement(By.cssSelector("[type=\"tel\"]")).sendKeys("+790");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actualResult = driver.findElement(By.cssSelector(".input_invalid")).getText();
        Assertions.assertNotNull(actualResult);
    }
}
