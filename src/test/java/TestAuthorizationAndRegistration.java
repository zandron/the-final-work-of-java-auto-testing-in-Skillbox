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

public class TestAuthorizationAndRegistration {
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
    private String pageTitleLocator = "Мой аккаунт";
    private String pageTitleRegLocator = "Регистрация";
    private String titleTrueRegistration = "Регистрация завершена";
    private String messErrorZeroDatalogin = "Имя пользователя обязательно";
    private String messErrorNoDateLogin = "Пароль обязателен";
    private String messErrorRandomDateLogin = "Неизвестное имя пользователя. Попробуйте еще раз или укажите адрес почты.";
    private String messErrorLoginFalsePass = "Веденный пароль для пользователя";
    private String messErrorRegZeroDate = "Пожалуйста, введите корректный email";
    private String messErrorDablLogin = "Учетная запись с таким именем пользователя уже зарегистрирована";
    private String messErrorDablEmail = "Учетная запись с такой почтой уже зарегистировавана";
    private By buttonEntranceLocator = By.xpath("//*[@class='account'][@href='http://intershop5.skillbox.ru/my-account/']");
    /*Вход в оконо авторизации через главное меню*/
    private By buttonPageMenuEntranceLocator = By.xpath("//div[@id='menu']//a[@href='http://intershop5.skillbox.ru/my-account/']");
    /*Вход в окно авторизации через Мой аккаунт в меню из подвала*/
    private By buttonFooterMenuEntranceLocator = By.xpath("//footer//a[@href='http://intershop5.skillbox.ru/my-account/']");
    /*3 локатора: поля логин пароль и кнопка вход*/
    private By inputLoginLocator = By.xpath("//input[@id='username']");
    private By inputPasswordLocator = By.xpath("//input[@id='password']");
    private By buttonWindowsEntranceLocator = By.xpath("//button[contains(@class, 'woocommerce-form-login__submit')]");
    private By textWellcomLocator = By.xpath("//div[@class='welcome-user']//span[@class='user-name']");
    private By nameloginWelcomLocator = By.xpath("//div[@class='woocommerce-MyAccount-content']//strong");
    private By titleHeaderLocator = By.xpath("//div[@id='accesspress-breadcrumb']//span");
    /*Кнопка вход в форме авторизации*/
    private By btnLoginLocator = By.xpath("//form//button[contains(@class,'woocommerce-form-login__submit')]");
    /*Поле вывода ошибки*/
    private By messErrorloginLocator = By.xpath("//ul[contains(@class,'woocommerce-error')]//li");
    /*Локатор кнопки регистрации в форме авторизации*/
    private By btnRegIsAutorizationWindowsLocator = By.xpath("//button[@class='custom-register-button']");
    /*Локатор кнопки регистрации с подвала сайта*/
    private By btnRegistrationLocator = By.xpath("//a[@href='http://intershop5.skillbox.ru/register/']");
    /*Кнопка зарегистрироваться в форме регистрации*/
    private By btnSubminRegistraitonLocator = By.xpath("//button[@type='submit'][@name='register']");
    /*Локатор поля ввода логина в форме регистрации*/
    private By inputLoginIsRegistrationLocator = By.xpath("//input[@id='reg_username']");
    /*Локатор поля ввода пароля в форме регистрации*/
    private By inputPasswordIsRegistrationLocator = By.xpath("//input[@id='reg_password']");
    /*Локатор поля ввода email в форме регистрации*/
    private By inputEmailIsRegistrationLocator = By.xpath("//input[@id='reg_email']");
    /*Локатор ссылки для возможности авторизоваться если почта есть в системе*/
    private By linkRegToAutorization = By.xpath("//a[@class='showlogin']");
    /*Локатор поля информации об успешной регистрации*/
    private By textTrueRegistration = By.xpath("//div[@class='content-page']");

    @Test
    public void test_authorization() {
        /*Осуществяет проверку никнейма после авторизации на сайте*/
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();

        driver.findElement(buttonEntranceLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("Did not go to the authorization window from the login button",
                tmpValue.equalsIgnoreCase(pageTitleLocator));
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputLoginLocator));
        driver.findElement(inputLoginLocator).sendKeys(loginname);
        driver.findElement(inputPasswordLocator).sendKeys(passordtext);
        driver.findElement(buttonWindowsEntranceLocator).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(textWellcomLocator));

        Assert.assertTrue("after authorization, it shows a visible nickname",
                (loginname.equalsIgnoreCase(driver.findElement(textWellcomLocator).getText())));
        Assert.assertTrue("after authorization, it shows a visible nickname",
                (loginname.equalsIgnoreCase(driver.findElement(nameloginWelcomLocator).getText())));
    }
    @Test
    public void TestEntrancePageMenu () {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(buttonPageMenuEntranceLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("Did not switch from the main menu to the authorization window ",
                tmpValue.equalsIgnoreCase(pageTitleLocator));
    }
    @Test
    public void TestEntranceFooterMenu () {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(buttonFooterMenuEntranceLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("Did not go from the link My account located in the basement of the site to the authorization window",
                tmpValue.equalsIgnoreCase(pageTitleLocator));

    }
    @Test
    public void TestFormAutorization() {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(buttonFooterMenuEntranceLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("Did not go from the link My account located in the basement of the site to the authorization window",
                tmpValue.equalsIgnoreCase(pageTitleLocator));
        /*Пробуем авторизоваться без введение данных*/
        driver.findElement(btnLoginLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки авторизоваться без ввода данных*/
        Assert.assertTrue("The error message is not clear not by spec", tmpValue.contains(messErrorZeroDatalogin));

        /*Пробуем авторизоваться с не корректным логином который не зарегистрирован в системе*/
        driver.findElement(inputLoginLocator).sendKeys("Login");
        driver.findElement(btnLoginLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки авторизоваться с логином которого нет в базе и произвольным паролем*/
        Assert.assertTrue("Error message without password is not on spec", tmpValue.contains(messErrorNoDateLogin));

        /*Пробуем авторизоваться с логином который случайный*/
        driver.findElement(inputLoginLocator).sendKeys("Login");
        driver.findElement(inputPasswordLocator).sendKeys("pass");
        driver.findElement(btnLoginLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки авторизоваться без ввода данных*/
        Assert.assertTrue("When entering a random login and entering a password, a message is displayed not by spec", tmpValue.contains(messErrorRandomDateLogin));

        /*Пробуем авторизоваться с логином который есть в базе но не ликвидный пароль*/
        driver.findElement(inputLoginLocator).clear();
        driver.findElement(inputLoginLocator).sendKeys(loginname);
        driver.findElement(inputPasswordLocator).sendKeys("pass");
        driver.findElement(btnLoginLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки авторизоваться логин есть в базе но не ликвидный пароль*/
        Assert.assertTrue("When you enter a legitimate login and a random password, a message is displayed not by spec", tmpValue.contains(messErrorLoginFalsePass));

        /*Переход в форму регистрации с формы Авторизации*/
        driver.findElement(btnRegIsAutorizationWindowsLocator).click();
        tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("By clicking the registration button, they did not switch from the authorization form to registration",
                tmpValue.equalsIgnoreCase(pageTitleRegLocator));

    }
    @Test
    public void TestFormReg() {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(btnRegistrationLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("By clicking the registration button, they did not switch from the authorization form to registration",
                tmpValue.equalsIgnoreCase(pageTitleRegLocator));

        /*Пробуем зарегистрироваться без ввода данных*/
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки регистрации без ввода данных*/
        Assert.assertTrue("When you try to register without entering data, a message is displayed that is not on spec", tmpValue.contains(messErrorRegZeroDate));

        /*Пробуем зарегистрироваться введя только логин*/
        driver.findElement(inputLoginIsRegistrationLocator).sendKeys(loginname);
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки регистрации только с логином*/
        Assert.assertTrue("When you try to register by entering only a login, a message is displayed not by spec", tmpValue.contains(messErrorRegZeroDate));

        /*Пробуем зарегистрироваться введя только логин и пароль*/
        driver.findElement(inputPasswordIsRegistrationLocator).sendKeys(passordtext);
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки регистрации без ввода почты*/
        Assert.assertTrue("When you try to register without entering email data, a message is not displayed by specе", tmpValue.contains(messErrorRegZeroDate));

        /*Пробуем зарегистрироваться введя только дабл логин, пароль, случайную почту*/
        driver.findElement(inputEmailIsRegistrationLocator).sendKeys("test111111dablLogin@tester.ru");
        driver.findElement(inputPasswordIsRegistrationLocator).sendKeys(passordtext);
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки регистрации без ввода данных*/
        Assert.assertTrue("When you try to register the input of a double login data, a message is displayed not by spec", tmpValue.contains(messErrorDablLogin));
    }
    @Test
    /*Тест отлавливает баг */
    public void TestTransitionFromRegistrationToAuthorization () {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(btnRegistrationLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("By clicking the registration button, they did not switch from the authorization form to registration",
                tmpValue.equalsIgnoreCase(pageTitleRegLocator));
        /*Пробуем зарегистрироваться введя только логин и пароль и дабл почты*/

        driver.findElement(inputLoginIsRegistrationLocator).sendKeys("1234");
        driver.findElement(inputEmailIsRegistrationLocator).sendKeys(emailtext);
        driver.findElement(inputPasswordIsRegistrationLocator).sendKeys(passordtext);
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(messErrorloginLocator).getText();
        /*Проверка сообщения для вывода ошибки при попытки регистрации без ввода данных*/
        Assert.assertTrue("When you try to register without entering email data, a message is displayed not by spec", tmpValue.contains(messErrorDablEmail));
        driver.findElement(linkRegToAutorization).click();
    }
    @Test
    /*Успешная регистрация требует смены логина и пароля  */
    public void TestTrue () {
        driver.navigate().to("http://intershop5.skillbox.ru/");
        driver.manage().window().maximize();
        driver.findElement(btnRegistrationLocator).click();
        var tmpValue = driver.findElement(titleHeaderLocator).getText();
        Assert.assertTrue("By clicking the registration button, they did not switch from the authorization form to registration",
                tmpValue.equalsIgnoreCase(pageTitleRegLocator));
        driver.findElement(inputLoginIsRegistrationLocator).sendKeys("NewUser11");
        driver.findElement(inputEmailIsRegistrationLocator).sendKeys("NewUser11@test.ru");
        driver.findElement(inputPasswordIsRegistrationLocator).sendKeys("123321");
        driver.findElement(btnSubminRegistraitonLocator).click();
        tmpValue = driver.findElement(textTrueRegistration).getAttribute("innerText");
        Assert.assertTrue("With legitimate data, registration was not carried out",
                tmpValue.equalsIgnoreCase(titleTrueRegistration));

    }

}
