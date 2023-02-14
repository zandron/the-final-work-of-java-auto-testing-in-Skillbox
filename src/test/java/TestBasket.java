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
import java.util.concurrent.TimeUnit;


public class TestBasket {

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
    /*локатор для перехода в каталог товаров*/
    private By allShopLocator = By.xpath("//aside[@id='pages-2']//a[contains(@href,'/shop/')]");
    /*локатор массива кнопок добавить в корзину*/
    private By addCartLocator = By.xpath("//div[@class='wc-products']//a[contains(@class,'add_to_cart_button')]");
    /*локатор массива названия товаров у которых кнопка добавить в корзину*/
    private By titleProductAddCart =
            By.xpath("//div[@class='wc-products']//a[contains(@class,'add_to_cart_button')]/ancestor::div[@class='collection_desc clearfix']//h3");
    /*Локатор перехода в корзину с главного меню*/
    private By cartLocator = By.xpath("//ul[@id='menu-primary-menu']//a[contains(@href,'cart/')]");
    /*Локатор название продуката в корзине*/
    private By nameProductIsCartLocator = By.xpath("//td[@class='product-name']/a");
    /*Локатор поля с колличеством товара*/
    private By valueCountProductCartlocator = By.xpath("//td[@class='product-quantity']//input");
    /*Локатор суммы заказа в таблице*/
    private By orderAmountTableLocator = By.xpath("//td[@class='product-subtotal']//bdi");
    /*Локатор суммы заказа*/
    private By orderAmountLocator = By.xpath("//div[@class='cart-collaterals']//bdi");
    /*Поле для ввода промокода*/
    private By inputPromoLocator = By.xpath("//input[@id='coupon_code']");
    /*Кнопка для применения промокода*/
    private By bntPromoLocator = By.xpath("//input[@id='coupon_code']/following-sibling::button");
    private String promocod = "SERT500";
    /*Кнопка удаление товара*/
    private By removeProductLocator = By.xpath("//td[@class='product-remove']//a");
    /*Кнопка возврата товара в корзину после удаления */
    private By restoreRemoveProductLocator = By.xpath("//a[@class='restore-item']");
    /*Название продукта который нужно вернуть в корзину*/
    private By nameProductRestoreForm = By.xpath("//div[@class='woocommerce-message']");
    /*Поле с информацией о добавлении купона*/
    private By infCuponLocator = By.xpath("//tr[contains(@class,'coupon-sert500')]");
    /*Поле информация о пустоте корзины*/
    private By zeroCartInfoLocator = By.xpath("//p[@class='cart-empty woocommerce-info']");
    /*Кнопка оформить заказ*/
    private By btnPlaceAnOrderLocator = By.xpath("//a[@class='checkout-button button alt wc-forward']");
    /*Поле с информацией "Оформление заказа*/
    private By titleChapteLocator = By.xpath("//div[@id='accesspress-breadcrumb']//span");
    private String titleChapteOrderLocator = "Оформление заказа";
    /*Локатор кнопки подробнее появляющийся после нажатия кнопки в корзину*/
    private By bntMoreDetailsLocator = By.xpath("//a[@class='added_to_cart wc-forward']");
    /*Локатор стрпоки купона в таблице*/
    private By trCuopunLocator = By.xpath("//tr[@class='cart-discount coupon-sert500']");
    @Test
    public void TestAddProductBasketnoAvtorization () throws InterruptedException {
        /*Проверяемя верхнее меню на соответствие категории и категории на странице куда ведет для всего меню
         * если находит не соответствие ты выводит в какой категории оно найденно*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(cartLocator).click();
        var listLocatorProductCart = driver.findElements(removeProductLocator);
        for (int i = 0; i < listLocatorProductCart.size(); i++) {
            /*получили название каталога*/
            var listMenu = driver.findElements(removeProductLocator).get(0);;
            listMenu.click();
        }
        driver.findElement(allShopLocator).click();
        /*проверяем при переходе в каталог товаров имеетсяли товар который можно добавить в корзину*/
        Assert.assertFalse("Нет товаров с кнопкой добавить в корзину при входе в каталог",
                (driver.findElements(addCartLocator).size() == 0));
        var nameProduct = driver.findElements(titleProductAddCart).get(0).getAttribute("innerText");
        /*Добавляем товар корзину*/
        driver.findElement(addCartLocator).click();
        /*ждем пока товар добавится в корзину*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(bntMoreDetailsLocator));
        /*Переходим в корзину*/
        driver.findElement(cartLocator).click();
        /*Проверяем название ранее добавленного товара совпадает с товаром в корзине*/
        Assert.assertTrue("Название добавленного товара в корзину не совпадает с товаром в корзине",
                (nameProduct.equalsIgnoreCase(driver.findElement(nameProductIsCartLocator).getText())));
        /*получаем значение текущей общей стоимости из таблици*/
        var priceProduct = driver.findElement(orderAmountTableLocator).getAttribute("innerText");
        /*Получаем общую стоимость корзины */
        var priceAll = driver.findElement(orderAmountLocator).getAttribute("innerText");
        priceAll = priceAll.substring(0,priceAll.length() - 1);
        priceProduct = priceProduct.substring(0,priceProduct.length() - 1);
        priceAll = priceAll.replace(",",".");
        priceProduct = priceProduct.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        var priceProductN = Double.parseDouble(priceProduct);
        var priceAllN = Double.parseDouble(priceAll);

        /*увеличиваем колличество товаров на 2*/
        driver.findElement(valueCountProductCartlocator).clear();
        driver.findElement(valueCountProductCartlocator).sendKeys("2");
        /*Выполняем пересчет стоимости корзины*/
        driver.findElement(valueCountProductCartlocator).sendKeys(Keys.ENTER);
        /*ждем изменение стоимости корзины*/
        TimeUnit.SECONDS.sleep(1);
        /*Получаем новое значение товара*/
        priceProduct = driver.findElement(orderAmountTableLocator).getAttribute("innerText");
        priceAll = driver.findElement(orderAmountLocator).getAttribute("innerText");
        priceProduct = priceProduct.substring(0,priceProduct.length() - 1);
        priceAll = priceAll.substring(0,priceAll.length() - 1);
        priceProduct = priceProduct.replace(",",".");
        priceAll = priceAll.replace(",",".");
        var priceProductEnd = Double.parseDouble(priceProduct);
        var priceAllEnd = Double.parseDouble(priceAll);
        priceAllN = priceAllN * 2;
        priceProductN = priceProductN * 2;
        /*сравнить правильно ли посчиталась сумма за два товара*/
        Assert.assertTrue("Сумма за 2 товара не правильно писчиталась", (priceProductN == priceProductEnd));
        /*В общем заказе сумма*/
        Assert.assertTrue("Общая сумма за 2 товара не правильно писчиталась в заказе", (priceAllN == priceAllEnd));
        /*Применяем купон*/
        /*Ввести купон*/
        driver.findElement(inputPromoLocator).sendKeys(promocod);
        /*Нажать кнопку применить купон*/
        driver.findElement(bntPromoLocator).click();
        /*Ждем применение купона*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(trCuopunLocator));
        /*обновляем данные по общей сумме заказа*/
        priceAll = driver.findElement(orderAmountLocator).getAttribute("innerText");
        priceAll = priceAll.substring(0,priceAll.length() - 1);
        priceAll = priceAll.replace(",",".");
        priceAllN = priceAllEnd - 500;
        priceAllEnd = Double.parseDouble(priceAll);
        /*В общем заказе сумма*/
        Assert.assertTrue("Общая сумма заказа после применение промокода поменялась на сумму не по скидке", (priceAllN == priceAllEnd));
        /*Проверяем появился ли введенный купон в таблице расчета стоимости заказа*/
        Assert.assertTrue("Купон не вывелся в таблице расчета стоимости заказа", driver.findElement(infCuponLocator).isDisplayed());

        /*Нажимаем кнопку удалить товар из корзины*/
        driver.findElement(removeProductLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(zeroCartInfoLocator));
        var nameValueCart = driver.findElement(zeroCartInfoLocator).getAttribute("innerText");
        Assert.assertTrue("Сообщение о пустоте корзины не читаемо", nameValueCart.contains("Корзина пуста."));

        /*Проверяем какой товар нам предлагают вернуть в корзину*/
        nameValueCart = driver.findElement(nameProductRestoreForm).getAttribute("innerText");
        Assert.assertTrue("Сообщение о возврате товара не содержит товара из корзины", nameValueCart.contains(nameProduct));

        /*возвращаем товар в корзину*/
        driver.findElement(restoreRemoveProductLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameProductIsCartLocator));
        /*Проверяем название ранее добавленного товара совпадает с товаром который ранее удаляли в корзине*/
        Assert.assertTrue("Название добавленного товара в корзину не совпадает с товаром в корзине",
                (nameProduct.equalsIgnoreCase(driver.findElement(nameProductIsCartLocator).getText())));

        /*Переход к стадии оформления*/
        driver.findElement(btnPlaceAnOrderLocator).click();
        Assert.assertTrue("Не перешли на страницу оформления заказа",
                titleChapteOrderLocator.equalsIgnoreCase(driver.findElement(titleChapteLocator).getText()));
    }

}
