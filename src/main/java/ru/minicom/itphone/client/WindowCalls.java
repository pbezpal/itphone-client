package ru.minicom.itphone.client;

import io.qameta.allure.Step;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class WindowCalls extends WindowMain{

    private WebDriver driver;
    private Robot robot = null;
    private Actions action = null;
    private By windowCallsBy = By.xpath("ancestor-or-self::*[@ClassName='TfrmFav']");
    private By listCallsBy = By.xpath("descendant::*[@ClassName='TfraCall']");
    private By contextMenuBy = By.className("#32768");
    private By itemsContextMenuBy = By.xpath("*");
    private String[] windows = new String[] {"Все вызовы", "Входящие вызовы", "Исходящие вызовы", "Пропущенные вызовы"};
    private Pattern imgButtonWindowAllCalls = new HelperClass("button_Window_All_Calls.jpg").getPattern();
    private Pattern imgButtonWindowInputCalls = new HelperClass("button_Window_Input_Calls.jpg").getPattern();
    private Pattern imgButtonWindowOutputCalls = new HelperClass("button_Window_Output_Calls.jpg").getPattern();
    private Pattern imgButtonWindowMissedCalls = new HelperClass("button_Window_Missed_Calls.jpg").getPattern();
    private Pattern imgButtonExitWindowCalls = new HelperClass("button_Exit_Window_Search_Contacts.jpg").getPattern();
    private Pattern imgButtonOpenMissedWindow = new HelperClass("button_Open_Missed_Calls.jpg").getPattern();
    private Pattern[] buttons = new Pattern[] {imgButtonWindowAllCalls, imgButtonWindowInputCalls, imgButtonWindowOutputCalls, imgButtonWindowMissedCalls};
    private Map<String, Pattern> windowCall = new LinkedHashMap<>();
    private String login;
    private WebElement element;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    //Инициализация объектов
    public WindowCalls(WebDriver driver, String login) {
        super(driver, login);
        this.driver = driver;
        this.login = login;
    }

    @Step("Вызываем окно 'Все вызовы' через горячие клавиши")
    public WindowCalls callWindowCallsHotButtons() {
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_F5);
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_F5);

        return this;
    }

    @Step("Вызываем окно 'Все вызовы' через главное меню")
    public WindowCalls callWindowCallsMainMenu() {
        List<WebElement> itemMenu = clickMainMenu();
        if (itemMenu.size() > 0) itemMenu.get(0).click();
        else System.out.println("Main menu is not available");

        return this;
    }

    private WebElement getWindowCalls(){
        return isWaitElement(windowCallsBy, 30, 1);
    }

    @Step(value = "Ждем, когда появится окно 'Все вызовы'")
    public boolean isAvailableWindowCalls(){
        if(isWaitElement(30, "", 4)) return isWaitElement(windowCallsBy, 30, "", 8);
        else return false;
    }

    @Step(value = "Проверяем, действительно ли появилось окно с названием {nameWindow}")
    public boolean isCheckNameWindow(String nameWindow){
        if(isAvailableWindowCalls()) return isWaitElement(windowCallsBy, 30, nameWindow, 4);
        else return false;
    }

    @Step(value = "Проверяем, есть ли список вызванных контактов")
    public boolean isElementCalls(){
        try{
            getWindowCalls().findElement(listCallsBy);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    @Step
    private List<WebElement> getElementsCalls(){
        return getWindowCalls().findElements(listCallsBy);
    }

    @Step
    public WindowMain doubleClickElementCalls(){
        if(isAvailableWindowCalls()){
            try{
                getElementsCalls();
                int elementsCall = getElementsCalls().size();
                action = new Actions(driver);
                action.doubleClick(getElementsCalls().get(elementsCall-1)).perform();
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }
        }

        return new WindowMain(driver, login);
    }

    @Step
    public boolean isButtonOpenMissedWindow(){
        return new HelperClass(imgButtonOpenMissedWindow).isGetPattern();
    }

    @Step
    public void clickButtonOpenMissedWindow(){
        new HelperClass(imgButtonOpenMissedWindow).clickPattern();
    }

    @Step
    private List<WebElement> getContextMenu(){
        if(isAvailableWindowCalls()){
            try{
                getElementsCalls();
                int elementsCall = getElementsCalls().size();
                action = new Actions(driver);
                action.contextClick(getElementsCalls().get(elementsCall-1)).perform();
                return driver.findElement(contextMenuBy).findElements(itemsContextMenuBy);
            }catch (NoSuchElementException e){
                return null;
            }
        }else return null;
    }

    public WindowMain clickItemsCallSubscriber(){
        List<WebElement> items = getContextMenu();
        if(items.size() > 0) items.get(0).click();

        return new WindowMain(driver, login);
    }

    @Step(value = "Ждем, когда окно 'Все вызовы' закроется")
    public boolean isWaitNotVisibilityWindowCalls(){
        return isWaitElement(windowCallsBy, 30, "", 11);
    }

    @Step(value = "Пробегаемся по окнам в напраелние {keyEvent}")
    public WindowCalls toRunOnWindow(int keyEvent) {
        robot.keyPress(keyEvent);
        robot.delay(100);
        robot.keyRelease(keyEvent);

        return this;
    }

    //Возрващаем массив атрибутов окон
    public String[] getWindowsName(){return windows;}

    //Возвращаем Map атрибутов окон и кнопок
    public Map<String, Pattern> getButtonAndWindow(){

        for(int i = 1; i < windows.length; i++){
            windowCall.put(windows[i], buttons[i]);
        }

        windowCall.put(windows[0], buttons[0]);

        return windowCall;
    }

    public void clickExitWindowCalls(){
        new HelperClass(imgButtonExitWindowCalls).clickPattern();
    }
}
