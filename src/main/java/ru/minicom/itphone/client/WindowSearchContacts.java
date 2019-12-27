package ru.minicom.itphone.client;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.List;

public class WindowSearchContacts extends WindowMain {

    private By iconAdditionalClientTreeBy = By.name("Поиск контакта");
    private By treeBy = By.name("Шеврон уведомления");
    private By windowSearchContactsBy = By.xpath("ancestor-or-self::*[@ClassName='TfrmFav']");
    private By listContactsBy = By.xpath("descendant::*[@ClassName='TfraFilterContact']");

    private WebDriver driver;
    private Robot robot;
    private Actions action = null;
    private String login;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public WindowSearchContacts(WebDriver driver, String login){
        super(driver, login);
        this.driver = driver;
        this.login = login;
    }

    public void clickAdditionalIconTree() {

        try{
            getIconPanelTree();
        }catch (NoSuchElementException e){
            driver.findElement(treeBy).click();
        }

        getIconPanelTree().findElement(iconAdditionalClientTreeBy).click();
    }

    @Step(value = "Ждём появления окна 'Поиск контактов'")
    public WebElement getWindowSearchContacts(){
        return isWaitElement(windowSearchContactsBy, 30, 1);
    }

    @Step(value = "Ждём закрытия окна 'Поиск контактов'")
    public boolean isWaitNotVisibilityWindowContacts(){
        return isWaitElement(windowSearchContactsBy, 30, "", 11);
    }

    public WebElement getSearchField(){
        return driver.switchTo().activeElement();
    }

    @Step(value = "Проверяем, появилось ли окно 'Поиск контактов'")
    public boolean isAvailableWindowSearchContacts() {
        if(isWaitElement(30, "", 4)) return isWaitElement(windowSearchContactsBy, 30, "", 8);
        else return false;
    }

    public WindowSearchContacts searchContacts(String contact){
        try{
            getWindowSearchContacts();
            getSearchField().sendKeys(contact);
        }catch (NoSuchElementException e){
            e.getMessage();
        }
        return this;
    }

    public boolean isElementFindContacts(){
        try {
            getWindowSearchContacts().findElement(listContactsBy);
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    private List<WebElement> getElementFindContact(){
        return getWindowSearchContacts().findElements(listContactsBy);
    }

    public WindowMain doubleClickFindContact(){
        if(isAvailableWindowSearchContacts()) {
            try{
                getElementFindContact();
                int contacts = getElementFindContact().size();
                action = new Actions(driver);
                action.doubleClick(getElementFindContact().get(contacts-1)).perform();
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }
        }

        return new WindowMain(driver, login);
    }

    public WindowSearchContacts callWindowSearchContactsHotButtons() {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_F6);
        robot.delay(1000);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyRelease(KeyEvent.VK_F6);

        return this;
    }

    public WindowSearchContacts callWindowSearchContactsMainMenu() {
        //Метод clickMainMenu наследован от класса WindowMain
        List<WebElement> itemMenu = clickMainMenu();
        if (itemMenu.size() > 0) itemMenu.get(2).click();
        else System.out.println("Main menu is available");

        return this;
    }
}
