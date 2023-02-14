import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestPlacingAnOrder {
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
    private String loginname = "lokki";
    private String passordtext = "12344321";
    private String emailtext = "test.r11j@kkk.ru";
    private String titleChapteOrderLocator = "Оформление заказа";
    private String promocod = "SERT500";
    private Double saveCoupon = 500.00;
    /*Данные для заполнения*/
    /*3 локатора: поля логин пароль и кнопка вход*/
    private By inputLoginLocator = By.xpath("//input[@id='username']");
    private By inputPasswordLocator = By.xpath("//input[@id='password']");
    private By buttonWindowsEntranceLocator = By.xpath("//button[contains(@class, 'woocommerce-form-login__submit')]");
    /*Кнопка вход для авторизации*/
    private By buttonEntranceLocator = By.xpath("//*[@class='account'][@href='http://intershop5.skillbox.ru/my-account/']");
    /*локатор для перехода в каталог товаров*/
    private By allShopLocator = By.xpath("//aside[@id='pages-2']//a[contains(@href,'/shop/')]");
    /*локатор массива кнопок добавить в корзину*/
    private By addCartLocator = By.xpath("//div[@class='wc-products']//a[contains(@class,'add_to_cart_button')]");
    /*локатор массива названия товаров у которых кнопка добавить в корзину*/
    private By titleProductAddCart =
            By.xpath("//div[@class='wc-products']//a[contains(@class,'add_to_cart_button')]/ancestor::div[@class='collection_desc clearfix']//h3");
    /*Локатор перехода в корзину с главного меню*/
    private By cartLocator = By.xpath("//ul[@id='menu-primary-menu']//a[contains(@href,'cart/')]");
    /*Локатор кнопки подробнее появляющийся после нажатия кнопки в корзину*/
    private By bntMoreDetailsLocator = By.xpath("//a[@class='added_to_cart wc-forward']");
    /*Локатор название продуката в корзине*/
    private By nameProductIsCartLocator = By.xpath("//td[@class='product-name']/a");
    /*Локатор суммы заказа*/
    private By orderAmountLocator = By.xpath("//div[@class='cart-collaterals']//bdi");
    /*Кнопка оформить заказ в корзине*/
    private By btnPlaceAnOrderLocator = By.xpath("//a[@class='checkout-button button alt wc-forward']");
    /*Поле с информацией "Оформление заказа*/
    private By titleChapteLocator = By.xpath("//div[@id='accesspress-breadcrumb']//span");
    /*Поле с приветствием после авторизации*/
    private By textWellcomLocator = By.xpath("//div[@class='welcome-user']//span[@class='user-name']");
    /*Кнопка удаление товара*/
    private By removeProductLocator = By.xpath("//td[@class='product-remove']//a");


    /*Блок локаторо относящиеся к странице оформления заказа*/
    /*Поля имя */
    private By inputNameOrderLocator = By.xpath("//input[@id='billing_first_name']");
    /*Поле фамилия */
    private By inputLastNameOrderLocator = By.xpath("//input[@id='billing_last_name']");
    /*Поле адрес*/
    private By inputAdrOrderLocator = By.xpath("//input[@id='billing_address_1']");
    /*Поле Город*/
    private By inputCityOrderLocator = By.xpath("//input[@id='billing_city']");
    /*Поле Область*/
    private By inputStateOrderLocator = By.xpath("//input[@id='billing_state']");
    /*Поле индекс*/
    private By inputPostcodeOrderLocator = By.xpath("//input[@id='billing_postcode']");
    /*Поле телефон*/
    private By inputPhoneOrderLocator = By.xpath("//input[@id='billing_phone']");
    /*Поле email*/
    private By inputEmailOrderLocator = By.xpath("//input[@id='billing_email']");
    /*Поле сумма заказа*/
    private By sumOrderLocator = By.xpath("//tr[@class='order-total']//bdi");
    /*Чек бокс по оплате Прямой банковский перевод*/
    private By checkPaymentMethodBacsLocator = By.xpath("//input[@id='payment_method_bacs']");
    /*Чек бокс по оплате Оплата при доставке*/
    private By checkPaymentMethodCodLocator = By.xpath("//input[@id='payment_method_cod']");
    /*Кнопка оформить заказ*/
    private By btnPlaceOrderLocator = By.xpath("//button[@id='place_order']");
    /*кнопка добавлени купона*/
    private By btnAddCouponOrderLocator = By.xpath("//a[@class='showcoupon']");
    private By infOrderLocator = By.xpath("//div[@id='primary']");
    private By textTrueOrderLocator = By.xpath("//div[@class='entry-content']//p[text()='Спасибо! Ваш заказ был получен.']");
    private By infOrderPaymentLocator = By.xpath("//li[@class='woocommerce-order-overview__payment-method method']//strong");
    private String payment1 = "Прямой банковский перевод";
    private String payment2 = "Оплата при доставке";

    private String textName = "Иванов";
    private String textlastName = "Иванов";
    private String textAdr = "ул. Центральная, 18";
    private String textCity = "Москва";
    private String textState = "Московская";
    private String textPostcode = "658565";
    private String textPhone = "+79052322522";
/*локаторы для купона*/
    private By formCopuonLocator = By.xpath("//form[@class='checkout_coupon woocommerce-form-coupon']");
    private By inputCouponLocator = By.xpath("//input[@id='coupon_code']");
    private By btnCouponLocator = By.xpath("//form[@class='checkout_coupon woocommerce-form-coupon']//button");
    /*Локатор стрпоки купона в таблице*/
    private By trCuopunLocator = By.xpath("//tr[@class='cart-discount coupon-sert500']");
    @Test
    public void TestPlacingAnOrder () throws InterruptedException {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
/*Автризация на сайте с проверкой на возможность ошибки при авторизации*/
        driver.findElement(buttonEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputLoginLocator));
        driver.findElement(inputLoginLocator).sendKeys(loginname);
        driver.findElement(inputPasswordLocator).sendKeys(passordtext);
        driver.findElement(buttonWindowsEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(textWellcomLocator));

        Assert.assertTrue("after authorization, it shows a visible nickname", (loginname.equalsIgnoreCase(driver.findElement(textWellcomLocator).getText())));
        /*Переходим в корзину и очистка ее если что то там есть*/
        driver.findElement(cartLocator).click();
        var listLocatorProductCart = driver.findElements(removeProductLocator);
        for (int i = 0; i < listLocatorProductCart.size(); i++) {
            /*получили название каталога*/
            var listMenu = driver.findElements(removeProductLocator).get(0);;
            listMenu.click();
        }
        /*Добавление товара для оформления заказа*/
        driver.findElement(allShopLocator).click();
        /*проверяем при переходе в каталог товаров имеетсяли товар который можно добавить в корзину*/
        Assert.assertFalse("Нет товаров с кнопкой добавить в корзину при входе в каталог",
                (driver.findElements(addCartLocator).size() == 0));
        var nameProduct = driver.findElements(titleProductAddCart).get(0).getAttribute("innerText");
        /*Добавляем товар корзину*/
        driver.findElement(addCartLocator).click();
        /*ждем пока товар добавится в корзину*/
       /* TimeUnit.SECONDS.sleep(1);*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(bntMoreDetailsLocator));
        /*Переходим в корзину*/
        driver.findElement(cartLocator).click();
        /*Проверяем название ранее добавленного товара совпадает с товаром в корзине*/
        Assert.assertTrue("Название добавленного товара в корзину не совпадает с товаром в корзине",
                (nameProduct.equalsIgnoreCase(driver.findElement(nameProductIsCartLocator).getText())));
        /*Получаем общую стоимость корзины */
        var priceAll = driver.findElement(orderAmountLocator).getAttribute("innerText");
        priceAll = priceAll.substring(0,priceAll.length() - 1);
        priceAll = priceAll.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        var priceAllN = Double.parseDouble(priceAll);

        /*Переход на страницу оформления заказа*/
        driver.findElement(btnPlaceAnOrderLocator).click();
         Assert.assertTrue("Не перешли на страницу оформления заказа",
                titleChapteOrderLocator.equalsIgnoreCase(driver.findElement(titleChapteLocator).getText()));

        /*Проверка страницы Оформить заказ*/
        /*В выпадающем меню выбираем старну Россия*/
        Select dropdown = new Select(driver.findElement(By.id("billing_country")));
        dropdown.selectByVisibleText("Russia");

        /*Закоментировать если до с учетки небыло раньше заказов в магазине*/
        var boolValue = true;
        /*Считываем значения полей и проверка сохраняются ли эти значение для повторного заказа
        (закоментировать если по данным с авторизации не делалось раньше заказов*/
        String[] myDate = new String[8];
        myDate[0] = driver.findElement(inputNameOrderLocator).getAttribute("Value");
        myDate[1] = driver.findElement(inputLastNameOrderLocator).getAttribute("Value");
        myDate[2] = driver.findElement(inputAdrOrderLocator).getAttribute("Value");
        myDate[3] = driver.findElement(inputCityOrderLocator).getAttribute("Value");
        myDate[4] = driver.findElement(inputStateOrderLocator).getAttribute("Value");
        myDate[5] = driver.findElement(inputPostcodeOrderLocator).getAttribute("Value");
        myDate[6] = driver.findElement(inputPhoneOrderLocator).getAttribute("Value");
        myDate[7] = driver.findElement(inputEmailOrderLocator).getAttribute("Value");

        var tmpStr = "";
        for (int i = 0; i < myDate.length; i++) {
            tmpStr = myDate[i];
            if (tmpStr.isEmpty()) boolValue = false;
        }
        Assert.assertTrue("Поля с предыдущего заказа не запоминаются", boolValue);
        /*Проверяем какая сумма заказа перенеслась из корзины в оформление заказа*/
        var priceAllOrder = driver.findElement(sumOrderLocator).getAttribute("innerText");
        priceAllOrder = priceAllOrder.substring(0,priceAllOrder.length() - 1);
        priceAllOrder = priceAllOrder.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        var priceAllNorder = Double.parseDouble(priceAllOrder);

        /*Проверяем какая сумма к оплате перенеслась у нас с корзины*/
        Assert.assertTrue("Сумма с корзины перенеслась не ликвидная", (priceAllNorder == priceAllN));
        if (!boolValue) {
            /*Заполнить поля*/
            driver.findElement(inputNameOrderLocator).clear();
            driver.findElement(inputLastNameOrderLocator).clear();
            driver.findElement(inputAdrOrderLocator).clear();
            driver.findElement(inputCityOrderLocator).clear();
            driver.findElement(inputStateOrderLocator).clear();
            driver.findElement(inputPostcodeOrderLocator).clear();
            driver.findElement(inputPhoneOrderLocator).clear();
            driver.findElement(inputEmailOrderLocator).clear();
            driver.findElement(inputNameOrderLocator).sendKeys(textName);
            driver.findElement(inputLastNameOrderLocator).sendKeys(textlastName);
            driver.findElement(inputAdrOrderLocator).sendKeys(textAdr);
            driver.findElement(inputCityOrderLocator).sendKeys(textCity);
            driver.findElement(inputStateOrderLocator).sendKeys(textState);
            driver.findElement(inputPostcodeOrderLocator).sendKeys(textPostcode);
            driver.findElement(inputPhoneOrderLocator).sendKeys(textPhone);
            driver.findElement(inputEmailOrderLocator).sendKeys(emailtext);
        }
        /*добавляем купон*/
        driver.findElement(btnAddCouponOrderLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCouponLocator));

        Assert.assertTrue("There was no opportunity to add a coupon", driver.findElement(formCopuonLocator).isDisplayed());

        /*Вводим данные по купону*/
        driver.findElement(inputCouponLocator).sendKeys(promocod);
        /*Нажать кнопку применить купон*/
        driver.findElement(btnCouponLocator).click();
        /*Ждем применение купона*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(trCuopunLocator));
        TimeUnit.SECONDS.sleep(1);
        priceAllOrder = driver.findElement(sumOrderLocator).getAttribute("innerText");
        priceAllOrder = priceAllOrder.substring(0,priceAllOrder.length() - 1);
        priceAllOrder = priceAllOrder.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        priceAllNorder = Double.parseDouble(priceAllOrder);
        priceAllN = priceAllN - saveCoupon;

        /*Проверяем какая сумма к оплате перенеслась у нас с корзины*/
        Assert.assertTrue("The amount was not recalculated when applying the coupon", (priceAllNorder == priceAllN));
        driver.findElement(checkPaymentMethodBacsLocator).click();
        driver.findElement(btnPlaceOrderLocator).click();
        /*Ждем пока перейдет на страницу оформления заказа*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(textTrueOrderLocator));
        /*Проверяем перешли ли мы на страницу информации об оформленном заказе */
        Assert.assertTrue("Заказ не оформлен", driver.findElement(textTrueOrderLocator).isDisplayed());
        tmpStr = driver.findElement(infOrderPaymentLocator).getAttribute("innerText");
        /*Проверяем совпадает ли выбранный способ оплаты нами ранее с таблице оформления заказа*/
        Assert.assertTrue("Информация об оплате не так которая выбрана для платы", payment1.contains(tmpStr));
    }
    @Test
    public void TestPlacingPaymen2AnOrder () throws InterruptedException {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        /*Автризация на сайте с проверкой на возможность ошибки при авторизации*/
        driver.findElement(buttonEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputLoginLocator));
        driver.findElement(inputLoginLocator).sendKeys(loginname);
        driver.findElement(inputPasswordLocator).sendKeys(passordtext);
        driver.findElement(buttonWindowsEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(textWellcomLocator));

        Assert.assertTrue("after authorization, it shows a visible nickname", (loginname.equalsIgnoreCase(driver.findElement(textWellcomLocator).getText())));
        /*Переходим в корзину и очистка ее если что то там есть*/
        driver.findElement(cartLocator).click();
        var listLocatorProductCart = driver.findElements(removeProductLocator);
        for (int i = 0; i < listLocatorProductCart.size(); i++) {
            /*получили название каталога*/
            var listMenu = driver.findElements(removeProductLocator).get(0);;
            listMenu.click();
        }
        /*Добавление товара для оформления заказа*/
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
        /*Получаем общую стоимость корзины */
        var priceAll = driver.findElement(orderAmountLocator).getAttribute("innerText");
        priceAll = priceAll.substring(0,priceAll.length() - 1);
        priceAll = priceAll.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        var priceAllN = Double.parseDouble(priceAll);

        /*Переход на страницу оформления заказа*/
        driver.findElement(btnPlaceAnOrderLocator).click();
        Assert.assertTrue("Не перешли на страницу оформления заказа",
                titleChapteOrderLocator.equalsIgnoreCase(driver.findElement(titleChapteLocator).getText()));

        /*Проверка страницы Оформить заказ*/
        /*В выпадающем меню выбираем старну Россия*/
        Select dropdown = new Select(driver.findElement(By.id("billing_country")));
        dropdown.selectByVisibleText("Russia");

        /*Закоментировать если до с учетки небыло раньше заказов в магазине*/
        var boolValue = true;
        /*Считываем значения полей и проверка сохраняются ли эти значение для повторного заказа
        (закоментировать если по данным с авторизации не делалось раньше заказов*/
        String[] myDate = new String[8];
        myDate[0] = driver.findElement(inputNameOrderLocator).getAttribute("Value");
        myDate[1] = driver.findElement(inputLastNameOrderLocator).getAttribute("Value");
        myDate[2] = driver.findElement(inputAdrOrderLocator).getAttribute("Value");
        myDate[3] = driver.findElement(inputCityOrderLocator).getAttribute("Value");
        myDate[4] = driver.findElement(inputStateOrderLocator).getAttribute("Value");
        myDate[5] = driver.findElement(inputPostcodeOrderLocator).getAttribute("Value");
        myDate[6] = driver.findElement(inputPhoneOrderLocator).getAttribute("Value");
        myDate[7] = driver.findElement(inputEmailOrderLocator).getAttribute("Value");

        var tmpStr = "";
        for (int i = 0; i < myDate.length; i++) {
            tmpStr = myDate[i];
            if (tmpStr.isEmpty()) boolValue = false;
        }
        Assert.assertTrue("Поля с предыдущего заказа не запоминаются", boolValue);
        /*Проверяем какая сумма заказа перенеслась из корзины в оформление заказа*/
        var priceAllOrder = driver.findElement(sumOrderLocator).getAttribute("innerText");
        priceAllOrder = priceAllOrder.substring(0,priceAllOrder.length() - 1);
        priceAllOrder = priceAllOrder.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        var priceAllNorder = Double.parseDouble(priceAllOrder);

        /*Проверяем какая сумма к оплате перенеслась у нас с корзины*/
        Assert.assertTrue("Сумма с корзины перенеслась не ликвидная", (priceAllNorder == priceAllN));
        if (!boolValue) {
            /*Заполнить поля*/
            driver.findElement(inputNameOrderLocator).clear();
            driver.findElement(inputLastNameOrderLocator).clear();
            driver.findElement(inputAdrOrderLocator).clear();
            driver.findElement(inputCityOrderLocator).clear();
            driver.findElement(inputStateOrderLocator).clear();
            driver.findElement(inputPostcodeOrderLocator).clear();
            driver.findElement(inputPhoneOrderLocator).clear();
            driver.findElement(inputEmailOrderLocator).clear();
            driver.findElement(inputNameOrderLocator).sendKeys(textName);
            driver.findElement(inputLastNameOrderLocator).sendKeys(textlastName);
            driver.findElement(inputAdrOrderLocator).sendKeys(textAdr);
            driver.findElement(inputCityOrderLocator).sendKeys(textCity);
            driver.findElement(inputStateOrderLocator).sendKeys(textState);
            driver.findElement(inputPostcodeOrderLocator).sendKeys(textPostcode);
            driver.findElement(inputPhoneOrderLocator).sendKeys(textPhone);
            driver.findElement(inputEmailOrderLocator).sendKeys(emailtext);
        }
        /*добавляем купон*/
        driver.findElement(btnAddCouponOrderLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCouponLocator));
        Assert.assertTrue("Не появилось возможность добавить купон", driver.findElement(formCopuonLocator).isDisplayed());

        /*Вводим данные по купону*/
        driver.findElement(inputCouponLocator).sendKeys(promocod);
        /*Нажать кнопку применить купон*/
        driver.findElement(btnCouponLocator).click();
        /*Ждем применение купона*/
        TimeUnit.SECONDS.sleep(1);
        priceAllOrder = driver.findElement(sumOrderLocator).getAttribute("innerText");
        priceAllOrder = priceAllOrder.substring(0,priceAll.length() - 1);
        priceAllOrder = priceAllOrder.replace(",",".");
        /*Преобразуем в число с плавающей точкой*/
        priceAllNorder = Double.parseDouble(priceAllOrder);
        priceAllN = priceAllN - saveCoupon;
        /*Проверяем какая сумма к оплате перенеслась у нас с корзины*/
        Assert.assertTrue("Сумма не пересчиталась при применении купона", (priceAllNorder == priceAllN));
        driver.findElement(checkPaymentMethodCodLocator).click();
        driver.findElement(btnPlaceOrderLocator).click();
        /*Ждем пока перейдет на страницу оформления заказа*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(textTrueOrderLocator));
        /*Проверяем перешли ли мы на страницу информации об оформленном заказе */
        Assert.assertTrue("Заказ не оформлен", driver.findElement(textTrueOrderLocator).isDisplayed());
        tmpStr = driver.findElement(infOrderPaymentLocator).getAttribute("innerText");
        /*Проверяем совпадает ли выбранный способ оплаты нами ранее с таблице оформления заказа*/
        Assert.assertTrue("Информация об оплате не так которая выбрана для платы", payment2.contains(tmpStr));
    }

}
