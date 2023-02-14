import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestProductCatalog {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp()
    {
        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @After
    public void tearDown() throws IOException {
        var sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        var path = "./screenshots/test.jpg";
        FileUtils.copyFile(sourceFile, new File(path));

        driver.quit();
    }

    private By listCategoryProductHomeMunulocator = By.xpath("//ul[@id='menu-primary-menu']//li/a[contains(@href,'/catalog/')]");
    private By nameCatalogyProductLocator = By.xpath("//header[@id='title_bread_wrap']//span");
    private By allShopLocator = By.xpath("//aside[@id='pages-2']//a[contains(@href,'/shop/')]");
    private By listCategoryLocator = By.xpath("//div[@id='woocommerce_product_categories-2']//a[contains(@href,'/product-category/')]");
    private By leftlistProductLocator = By.xpath("//div[@id='woocommerce_products-2']//li");
    private By listVisiblityProductLocator = By.xpath("//div[@class='wc-products']//li");
    private By inputSearchLocator = By.xpath("//div[@class='search-form']//input[@name='s']");
    private By btnSearchLocator = By.xpath("//button[@class='searchsubmit']");
    private String searchname = "Холод";
    private By titleListVidsibilityProductLocator = By.xpath("//div[@class='wc-products']//h3[contains(text(),'Холод')]");
    private By minValueFilterLocator = By.xpath("//input[@id='min_price']");
    @Test
    public void test_list_category_product() {
        /*Проверяемя верхнее меню на соответствие категории и категории на странице куда ведет для всего меню
        * если находит не соответствие ты выводит в какой категории оно найденно*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        var count = driver.findElements(listCategoryProductHomeMunulocator).size();
        for (int i = 0; i < count; i++) {
            /*получили название каталога*/
            var listMenu = driver.findElements(listCategoryProductHomeMunulocator).get(i);
            var name = listMenu.getAttribute("innerText");
            var valhref = listMenu.getAttribute("href");
            driver.navigate().to(valhref);
            var strerror = "The names of the selected category in the main menu and the page in the catalog do not match" + name;
            Assert.assertTrue(strerror, (name.equalsIgnoreCase(driver.findElement(nameCatalogyProductLocator).getText())));
            driver.navigate().to("http://intershop5.skillbox.ru/");
        }
    }
    @Test
    public void test_list_category_productIsCatalog() {
        /*Проверяемя  соответствие категории и категории в каталоге товаров на странице куда ведет для всего меню
          если находит не соответствие ты выводит в какой категории оно найденно*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(allShopLocator).click();
        var count = driver.findElements(listCategoryLocator).size();
        for (int i = 0; i < count; i++) {
            /*получили название каталога*/
            var listMenu = driver.findElements(listCategoryLocator).get(i);
            var name = listMenu.getAttribute("innerText");
            var valhref = listMenu.getAttribute("href");
            driver.navigate().to(valhref);
            var strerror = "The names of the selected category and the page in the catalog do not match" + name;
            Assert.assertTrue(strerror, (name.equalsIgnoreCase(driver.findElement(nameCatalogyProductLocator).getText())));
            driver.findElement(allShopLocator).click();
        }

    }
    @Test
    /*Тест отлавливает ошибку*/
    public void TestVisibilityElement() {
        /*проверяет наличие блока товары на страницы под списком категорий на странице категории товаров,
        ,правильность настройки минимального значения для фильтра товаров(не может быть отрицательного значения)*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(allShopLocator).click();
        Assert.assertTrue("the list of products under the list of product categories is not displayed",
                (driver.findElements(leftlistProductLocator).size() > 0));


    }
    @Test
    /*Тест отлавливает ошибку*/
    public void TestSearchProduct() {
        /*Провека поиска в каталоге товаров
        * 1. Поиск осуществляется по заданному слову
        * 2. Правильно ищет вхождения слово в название товара*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(inputSearchLocator).sendKeys(searchname);
        driver.findElement(btnSearchLocator).click();

        Assert.assertTrue("In the case of an intrigue , it is not the word that was entered that is transmitted",
                driver.findElement(nameCatalogyProductLocator).getText().contains(searchname));
        Assert.assertTrue("When searching for a part of a word in the product name, the search is performed partially",
                (driver.findElements(listVisiblityProductLocator).size() == driver.findElements(titleListVidsibilityProductLocator).size()));

    }

}
