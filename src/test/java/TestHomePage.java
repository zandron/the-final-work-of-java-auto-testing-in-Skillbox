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


public class TestHomePage {
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


    private By listCardDiscountsLocator = By.xpath("//section[@id='product1']//li[contains(@class, 'slick-slide')]");
    private By listlogotypeDiscountsIsCardLocator = By.xpath("//section[@id='product1']//li[contains(@class, 'slick-slide')]//span[@class='onsale']");
    private By listCardsPromoSectionLocator = By.xpath("//section[@id='promo-section1']//div[@class='promo-widget-wrap']");
    private By elementsLeadingToCatalogLocator = By.xpath("//section[@id='promo-section1']//a[contains(@href, 'catalog')]");
    private By pageMenuZeroLinkElementLocator = By.xpath("//ul[@id='menu-primary-menu']//a[contains(@href, ' ') or contains(@href, '#')]");
    private By footerMenuZeroLinkElementLocator = By.xpath("//footer//*[text()='Страницы сайта']//following::li//a[contains(@href, ' ') or contains(@href, '#')]");
    private By listCardsNewArrivalsSectionLocator = By.xpath("//section[@id='product2']//li[contains(@class, 'slick-slide')]");
    private By listLogotypeNewArrivalsLocator = By.xpath("//section[@id='product2']//li[contains(@class, 'slick-slide')]//span[@class='label-new']");

    @Test
    public void test_search_home_page_element() {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        /*Проверяет наличие логотипа скидки на всех товарах в блоке скидок*/
        Assert.assertTrue("discounts Not all products in the sale section have a discount logo installed",
                (driver.findElements(listCardDiscountsLocator).size() == driver.findElements(listlogotypeDiscountsIsCardLocator).size()));
        /*Провека секции промо на то что они ведут в каталог в определеную категорию*/
        Assert.assertTrue("not all elements of the promo section leading to the catalog",
                (driver.findElements(listCardsPromoSectionLocator).size() == driver.findElements(elementsLeadingToCatalogLocator).size()));
        /*проверяет наличие всех ссылок  ссылок в главном меню*/
        Assert.assertTrue("There are empty links in the main menu of the site",
                (driver.findElements(pageMenuZeroLinkElementLocator).size() == 0));
        /*проверка наличие всех ссылок в нижнем меню*/
        Assert.assertTrue("There are empty links in the footer menu of the site",
                (driver.findElements(footerMenuZeroLinkElementLocator).size() == 0));
        /*Проверка логотипа новый товар в секции новый товар*/
        Assert.assertTrue("not all elements of the promo section leading to the catalog",
                (driver.findElements(listCardsNewArrivalsSectionLocator).size() == driver.findElements(listLogotypeNewArrivalsLocator).size()));

    }
    private By elementCardShowLocator = By.xpath("//section[@id='product2']//li[contains(@class, 'slick-slide')][6]");
    private By nameTitleCardLocator = By.xpath("//section[@id='product2']//li[contains(@class, 'slick-slide')][6]//h3");
    private By elementMenuPagelinkLocator = By.xpath("//ul[@id='menu-primary-menu']//a[@href= 'http://intershop5.skillbox.ru/']");
    private By sectionApCatListLocator = By.xpath("//section[@class='ap-cat-list clear']//li");
    private By nameElementSectionApCatListOneLocator = By.xpath("//section[@class='ap-cat-list clear']//li[1]/a/span");
    @Test
    public void test_search_home_page_element_to_visibility() {
        /*Проверяет появление секции промотренные товары, и что в нее добавился последний промотренный товар*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," + "document.body.scrollHeight,document.documentElement.clientHeight));");


        wait.until(ExpectedConditions.visibilityOfElementLocated(nameTitleCardLocator));
        var titleCar = driver.findElement(nameTitleCardLocator).getText().toUpperCase();
        driver.findElement(elementCardShowLocator).click();
        driver.findElement(elementMenuPagelinkLocator).click();
        var nameValut = driver.findElement(nameElementSectionApCatListOneLocator).getText().toUpperCase();

        Assert.assertTrue("a block of previously viewed products did not appear", (driver.findElements(sectionApCatListLocator).size()) > 0);
        Assert.assertTrue("Visiblity not name card", (titleCar.equalsIgnoreCase(nameValut)));
    }
    private By buttonEntranceLocator = By.xpath("//*[@class='account'][@href='http://intershop5.skillbox.ru/my-account/']");
    private By inputLoginLocator = By.xpath("//input[@id='username']");
    private By inputPasswordLocator = By.xpath("//input[@id='password']");
    private By buttonWindowsEntranceLocator = By.xpath("//button[contains(@class, 'woocommerce-form-login__submit')]");
    private By textWellcomLocator = By.xpath("//div[@class='welcome-user']//span[@class='user-name']");
    @Test
    public void test_authorization() {
        /*Осуществяет проверку никнейма после авторизации на сайте*/
        var loginname = "lokki";
        var passordtext = "12344321";
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();

        driver.findElement(buttonEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputLoginLocator));
        driver.findElement(inputLoginLocator).sendKeys(loginname);
        driver.findElement(inputPasswordLocator).sendKeys(passordtext);
        driver.findElement(buttonWindowsEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(textWellcomLocator));

        Assert.assertTrue("after authorization, it shows a visible nickname", (loginname.equalsIgnoreCase(driver.findElement(textWellcomLocator).getText())));
    }
}
