package ru.minicom.itphone.client;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.List;
import java.util.NoSuchElementException;

public class WindowMain extends WindowLogin {

    private WebDriver driver;
    private Robot robot;
    private Actions action = null;

    private By loginWindowBy = By.className("TfrmLogin");
    private By treeBy = By.name("Шеврон уведомления");
    private By mainMenuBy = By.className("#32768");
    private By parentAreaIconsTreeBy = By.className("NotifyIconOverflowWindow");
    private By panelTreeIconsBy = By.className("ToolbarWindow32");
    private By callSubscriberBy = By.xpath("descendant-or-self::*[@ClassName='TfrmPultCall']");
    private By itemsMainMenuBy = By.xpath("*");
    private By iconClientTreeBy;
    private Pattern imgTurnMainWindow = new HelperClass("button_Turn_Main_Window.jpg").getPattern();
    private Pattern imgSearchContacts = new HelperClass("button_Search_Contacts_In_Main_Window.jpg").getPattern();
    private Pattern imgLateCalls = new HelperClass("button_Late_Calls_In_Main_Window.jpg").getPattern();
    private Pattern imgButtonEndCall = new HelperClass("button_End_Call.jpg").getPattern();
    private Pattern imgCallMainMenu = new HelperClass("button_Main_Menu_In_Window_Menu.jpg").getPattern();
    private String classWindowMain = "TfrmMain";
    private String login;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public WindowMain(WebDriver driver, String login){
        super(driver);
        this.driver = driver;
        this.login = login;
        this.iconClientTreeBy = By.xpath("*[contains(@Name, '" + login + "')]");
    }

    @Step("Ждем, когда окно авторизации пропадёт с экрана")
    public boolean isWaitNotVisibleLoginWindow(){ return isWaitElement(loginWindowBy, 40, "", 11); }

    public String getLogin(){
        return this.login;
    }

    @Step("Вызываем главное окно программы через горячие кнопки")
    public WindowMain keyPressHotButtons() {
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_F1);
        robot.delay(1000);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_F1);

        return this;
    }

    @Step("Проверяем доступность области с иконками в Tree")
    public WebElement getIconPanelTree() {
        return driver.findElement(parentAreaIconsTreeBy).findElement(panelTreeIconsBy);
    }

    /*public void availableTreePanel(){
        if(! isWaitFormElement(getIconPanelTree(), 10)) driver.findElement(treeBy).click();
        System.out.println(driver.switchTo().activeElement().getAttribute("ClassName"));
    }*/

    @Step("Находим иконку клиента IT-Phone")
    public WebElement getIconClientPanelTree() {
        if(isWaitElement(parentAreaIconsTreeBy, 10, "", 11)) driver.findElement(treeBy).click();
        return getIconPanelTree().findElement(iconClientTreeBy);
    }

    @Step
    public boolean isAvailableIconClientTree(){
        return isWaitElement(iconClientTreeBy, 30, "", 7);
    }

    @Step("Находим главное окно программы")
    public boolean isMainWindow() {
        return isWaitElement(30, classWindowMain, 1);
    }

    @Step("Находим все пункты главного меню")
    public List<WebElement> clickMainMenu(){
        action = new Actions(driver);
        action.contextClick(getIconClientPanelTree()).perform();
        return driver.findElement(mainMenuBy).findElements(itemsMainMenuBy);
    }

    public boolean isAvailableMainMenu(){
        return isWaitElement(mainMenuBy, 60, "", 7);
    }

    @Step("Возвращаем элемент вызова абонента")
    public WebElement getElementCallSubscriber(){
        return isWaitElement(callSubscriberBy, 60, 1);
    }

    @Step("Проверяем есть ли элемент вызова абонента")
    public boolean isElementCallSubscriber(){
        return isWaitElement(getElementCallSubscriber(), 30, 1);
    }

    @Step("Проверяем пропал ли элемент вызова абонента")
    public boolean isNotElementCallSubscriber(){
        return isWaitElement(callSubscriberBy, 10, "", 13);
    }

    @Step("Завершаем вызов")
    public void clickButtonEndCall() {
        if(isElementCallSubscriber()) new HelperClass(imgButtonEndCall).clickPattern();
    }

    @Step("Закрываем главное окно программы")
    public void turnMainWindow() {
        new HelperClass(imgTurnMainWindow).clickPattern();
        new HelperClass().mouseMove(70.0, 50.0);
    }

    @Step("Открываем окно 'Поиск контактов'")
    public WindowSearchContacts clickSearchWindow() {
        new HelperClass(imgSearchContacts).clickPattern();

        return new WindowSearchContacts(driver, login);
    }

    @Step("Открываем окно 'Все вызовы'")
    public WindowCalls clickLateCallsWindow() {
        new HelperClass(imgLateCalls).clickPattern();

        return new WindowCalls(driver, login);
    }

    public WindowMain clickButtonMainMenu(){
        new HelperClass(imgCallMainMenu).clickPattern();

        return this;
    }

    @Step("Закрываем программу")
    public void clickExitButton() {
        List<WebElement> itemMenu = clickMainMenu();
        if(itemMenu.size() > 0) itemMenu.get(itemMenu.size() - 1).click();
        else System.out.println("Not can to exit from application");
    }
}

