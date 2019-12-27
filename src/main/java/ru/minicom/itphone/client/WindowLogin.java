package ru.minicom.itphone.client;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.sikuli.script.Pattern;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

class WindowLogin extends HelperClass {

    //Объявляем переменные
    private WebDriver driver;
    private By loginWindowBy = By.className("TfrmLogin");
    private By editFieldBy = By.className("TsEdit");
    private By serverFieldBy = By.className("TsComboEdit");
    private By enterButtonBy = By.className("TsBitBtn");
    private By windowLookServersBy = By.className("TfrmServerFinder");
    private By listServersBy=By.xpath("descendant::*[@ClassName='TfraServer']");
    private By windowSelectUserParamsBy = By.className("TfrmSelectUserParams");
    private By listSelectUserParamBy = By.className("TfraSelectUserParam");
    private By windowInvalidCredsBy = By.name("Неверная учетная запись или пароль");
    private By buttonOkInvalidCredsBy = By.name("ОК");
    private By windowEmptyServerBy = By.name("Введите адрес сервера.");
    private By windowInvalidServerBy = By.name("Связь с сервером контактов прервана.");
    private By confirmFormExitBy = By.name("Вы действительно хотите выйти из программы?");
    private By buttonYesExitBY = By.name("Да");
    private By buttonNoExitBY = By.name("Нет");
    private By buttonOkBy = By.name("Ок");
    private By buttonNoServerBy = By.name("Отмена");
    private Pattern imgExitClient = new HelperClass("authFormExit.JPG").getPattern();
    private Pattern imgLookServers = new HelperClass("lookServers.jpg").getPattern();
    private String login;
    private String password;
    private String server;

    //Конструктор класса WindowLogin
    public WindowLogin(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    //Проверяем, доступен ли элемент формы
    public boolean isAvailableElement(By locator){ return isWaitElement(locator, 30, "", 7); }

    public boolean isNotAvailableElement(By locator){ return isWaitElement(locator, 30, "", 11); }

    public boolean isAvailableLoginWindow(){ return isWaitElement(loginWindowBy, 30, "", 5); }

    @Step("Вводим в поле логин значение {login}")
    public WindowLogin setLogin(String login){
        this.login = login;
        if(isAvailableElement(editFieldBy) && ! login.isEmpty()) {
            driver.findElements(editFieldBy).get(1).clear();
            driver.findElements(editFieldBy).get(1).sendKeys(this.login);
        }

        return this;
    }

    @Step("Получаем данные в поле логин")
    public String getLogin(){
        if(isAvailableElement(editFieldBy)) return driver.findElements(editFieldBy).get(1).getText();
        else return "";
    }

    @Step("Очищаем поле логин")
    public WindowLogin clearLogin(){
        if(isAvailableElement(editFieldBy)) driver.findElements(editFieldBy).get(1).clear();
        return this;
    }

    @Step("Вводим в поле пароль значение {password}")
    public WindowLogin setPassword(String password){
        this.password = password;
        if(isAvailableElement(editFieldBy) && ! password.isEmpty()){
            driver.findElements(editFieldBy).get(0).clear();
            driver.findElements(editFieldBy).get(0).sendKeys(this.password);
        }

        return this;
    }

    public String getPassword(){
        if(isAvailableElement(editFieldBy)) return driver.findElements(editFieldBy).get(0).getText();
        else return "";
    }

    @Step("Очищаем поле пароль")
    public WindowLogin clearPassword(){
        if(isAvailableElement(editFieldBy)) driver.findElements(editFieldBy).get(0).clear();
        return this;
    }

    @Step("Вводим в поле сервер значение {server}")
    public WindowLogin setServer(String server) {
        this.server = server;
        if(isAvailableElement(serverFieldBy) && ! server.isEmpty()){
            driver.findElement(serverFieldBy).clear();
            driver.findElement(serverFieldBy).sendKeys(this.server);
        }

        return this;
    }

    @Step("Получаем данные в поле сервер")
    public String getServer(){
        if(isAvailableElement(serverFieldBy)) return driver.findElement(serverFieldBy).getText();
        else return "";
    }

    @Step("Очищаем поле сервер")
    public WindowLogin clearServer(){
        if(isAvailableElement(serverFieldBy)) driver.findElements(serverFieldBy).clear();
        return this;
    }

    public void exitAuth() {
        if(isAvailableConfirmForm()) driver.findElement(buttonYesExitBY).click();
        else if(isAvailableElement(loginWindowBy)) clickYesExitConfirmForm();
    }

    @Step("Проверяем, что появилось окно для подтверждения выхода")
    public boolean isAvailableConfirmForm(){ return isAvailableElement(confirmFormExitBy); }

    @Step("Подтверждаем выход из программы")
    public WindowLogin clickYesExitConfirmForm() {
        if(isAvailableElement(loginWindowBy)){
            new HelperClass(imgExitClient).clickPattern();
            if(isAvailableConfirmForm()){
                driver.findElement(buttonYesExitBY).click();
            }
        }

        return this;
    }

    @Step("Отмена выхода из программы")
    public WindowLogin clickNoExitConfirmForm() {
        if(isAvailableElement(loginWindowBy)){
            new HelperClass(imgExitClient).clickPattern();
            if(isAvailableConfirmForm()){
                driver.findElement(buttonNoExitBY).click();
            }
        }

        return this;
    }

    @Step("Заполняем все поля формы")
    public WindowLogin setDataFields(String login, String password, String server) {
        if( ! getLogin().equals(login)) setLogin(login);
        setPassword(password);
        if( ! getServer().equals(server)) setServer(server);

        return this;
    }

    @Step("Заполняем поля 'Учетная запись' и 'Пароль'. Сервер не заполняем")
    public WindowLogin setDataFields(String login, String password){
        if( ! getLogin().equals(login)) setLogin(login);
        setPassword(password);

        return this;
    }

    @Step("Кликаем на кнопку Войти")
    public void clickEnterButton(){
        if(isAvailableElement(enterButtonBy)) driver.findElement(enterButtonBy).click();
    }

    @Step("Проверяем, что появилось окно с ошибкой 'Неверная учетная запись или пароль'")
    public boolean isAvailableWindowWithInvalidCreds(){ return isAvailableElement(windowInvalidCredsBy); }

    @Step("Проверяем, что не появилось окно с ошибкой 'Неверная учетная запись или пароль'")
    public boolean isNotAvailableWindowWithInvalidCreds(){ return isNotAvailableElement(windowInvalidCredsBy); }

    @Step("Кликаем по кнопке Ок в окне 'Неверная учетная запись или пароль'")
    public void clickButtonWindowInvalidCreds(){
        if(isAvailableWindowWithInvalidCreds()) driver.findElement(buttonOkInvalidCredsBy).click();
    }

    @Step("Проверяем, что появилось окно с ошибкой 'Введите адрес сервера.'")
    public boolean isAvailableWindowEmptyServer(){ return isAvailableElement(windowEmptyServerBy); }

    @Step("Кликаем по кнопке Ок в окне 'Введите адрес сервера.'")
    public void clickButtonWindowEmptyServer(){
        if(isAvailableWindowEmptyServer()) driver.findElement(buttonOkInvalidCredsBy).click();
    }

    @Step("Проверяем, что появилось окно с ошибкой 'Связь с сервером контактов прервана.'")
    public boolean isAvailableWindowInvalidServer(){ return isAvailableElement(windowInvalidServerBy); }

    @Step("Проверяем, что не появилось окно с ошибкой 'Связь с сервером контактов прервана.'")
    public boolean isNotAvailableWindowInvalidServer(){ return isNotAvailableElement(windowInvalidServerBy); }

    @Step("Кликаем по кнопке Ок в окне Связь с сервером контактов прервана.'")
    public WindowLogin clickButtonWindowInwalidServer(){
        if(isAvailableWindowInvalidServer()) {
            driver.findElement(buttonOkInvalidCredsBy).click();
        }

        return this;
    }

    @Step(value = "Проверяем, что появилось окно выбора типа абонента")
    public boolean isAvailableWindowsSelectUserParams(){ return isAvailableElement(windowSelectUserParamsBy); }

    @Step(value = "Получаем список типов пользователей")
    public List<WebElement> getListSelectUserParam(){
        if(isAvailableWindowsSelectUserParams()){ return driver.findElement(windowSelectUserParamsBy).findElements(listSelectUserParamBy);}
        else return null;
    }

    @Step(value = "Выбираем тип контакта SIP")
    public void clickUserParam(){
        if(getListSelectUserParam() != null) {
            int countList = getListSelectUserParam().size();
            getListSelectUserParam().get(countList - 1).click();
            driver.findElement(buttonOkBy).click();
        }
    }

    @Step("Проверяем, что появилось окно поиска серверов")
    public boolean isAvailableWindowLookServers(){ return isAvailableElement(windowLookServersBy); }

    @Step("Получаем список серверов")
    public List<WebElement> getListServers(){
        if(isAvailableWindowLookServers()) return driver.findElement(windowLookServersBy).findElements(listServersBy);
        else return null;
    }

    @Step("Выбираем один из серверов")
    public WindowLogin clickServer(){
        if(getListServers() != null){
            getListServers().get(0).click();
        }

        return this;
    }

    @Step("Кликаем на кнопку 'Поиск сервера'")
    public WindowLogin clickButtonLookServers() {
        new HelperClass(imgLookServers).clickPattern();
        return this;
    }

    @Step("Кликаем по кнопке Ок  в окне выюора сервера")
    public WindowLogin clickButtonOkWindowLookServers(){
        if(isAvailableWindowLookServers()) driver.findElement(buttonOkBy).click();
        return this;
    }

    @Step("Кликаем по кнопке Нет в окне выюора сервера")
    public WindowLogin clickButtonExitWindowLookServers(){
        if(isAvailableWindowLookServers()) driver.findElement(buttonNoServerBy).click();
        return this;
    }

    @Step("Проверяем, что кнопка 'Войти' неактивная")
    public String isInactiveButtonEnter(){ return driver.findElement(enterButtonBy).getAttribute("IsEnabled"); }

    //Проверяем корректность авторизации
    public void checkOkAuthOnServer(){
        new WebDriverWait(driver, 60).until(presenceOfElementLocated(windowInvalidServerBy));
    }

    //Возвращаем длину логина
    public int getLengthTextLogin(){
        return driver.findElements(editFieldBy).get(1).getText().length();
    }
}
