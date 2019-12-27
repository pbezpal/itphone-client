package ru.minicom.itphone.client;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Pattern;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Calls extends HelperClass{

    private WebDriver driver;
    private StartTest startTest = new StartTest();
    private Actions actions;
    private WindowLogin windowLogin = null;
    private WindowMain windowMain = null;
    private Robot robot;
    private By nameIconClientBy = By.name("IT-Phone");
    private By contextMenuBy = By.className("#32768");
    private By propertiesBy = By.name("Свойства");
    private By textFieldBy = By.className("Edit");
    private By buttonOkBy = By.name("ОК");
    private By buttonContinuesBy = By.name("Продолжить");
    //private By windowMissedCallBy = By.xpath("descendant-or-self::*[@ClassName='TfrmAlert']");
    private By windowMissedCallBy = By.className("TfrmAlert");
    private Pattern imgButtonAnswerCall = new HelperClass("button_Answer_Call.jpg").getPattern();
    private Pattern imgButtonNoAnswerCall = new HelperClass("button_No_Answer_Call.jpg").getPattern();
    private Pattern imgOpenWindowMissedCalls = new HelperClass("button_Open_Missed_Calls.jpg").getPattern();
    private Pattern imgButtonOpenMissedWindow = new HelperClass("button_Open_Missed_Calls.jpg").getPattern();
    private String subscriber_SIP_A = startTest.getSipSubscribe_A();
    private String subscriber_SIP_B = startTest.getSipSubscribe_B();

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public Calls(WebDriver driver){
        super(driver);
        this.driver = driver;
        this.actions = new Actions(driver);
    }


    @Step
    public Calls callPropertiesClient(){
        if(isWaitElement(nameIconClientBy, 10, "",7)) {
            actions.contextClick(driver.findElement(nameIconClientBy)).build().perform();
            driver.findElement(contextMenuBy).findElement(propertiesBy).click();
        }

        return this;
    }

    public WebElement getElementTextObject(){
        return driver.findElements(textFieldBy).get(1);
    }

    @Step
    public void setPropertiesClient(String textObject){
        getElementTextObject().clear();
        getElementTextObject().sendKeys(textObject);
    }

    @Step
    public Calls clickOkPropertiesClient(){
        driver.findElement(buttonOkBy).click();
        if(isWaitElement(buttonContinuesBy, 10, "", 7)) driver.findElement(buttonContinuesBy).click();

        return this;
    }

    @Step
    public Calls clickIconClient(){
        actions.doubleClick(driver.findElement(nameIconClientBy)).build().perform();
        return this;
    }

    @Step
    public WindowMain dialSubscriber(String number){
        if(number.equals(subscriber_SIP_B)) {
            robot.keyPress(KeyEvent.VK_NUMPAD5);
            robot.keyPress(KeyEvent.VK_NUMPAD0);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_NUMPAD9);
            robot.keyPress(KeyEvent.VK_NUMPAD8);
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_NUMPAD5);
            robot.keyRelease(KeyEvent.VK_NUMPAD0);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_NUMPAD9);
            robot.keyRelease(KeyEvent.VK_NUMPAD8);
        }else{
            robot.keyPress(KeyEvent.VK_NUMPAD5);
            robot.keyPress(KeyEvent.VK_NUMPAD0);
            robot.delay(100);
            robot.keyPress(KeyEvent.VK_NUMPAD9);
            robot.keyPress(KeyEvent.VK_NUMPAD9);
            robot.delay(1000);
            robot.keyRelease(KeyEvent.VK_NUMPAD5);
            robot.keyRelease(KeyEvent.VK_NUMPAD0);
            robot.delay(100);
            robot.keyRelease(KeyEvent.VK_NUMPAD9);
            robot.keyRelease(KeyEvent.VK_NUMPAD9);
        }

        return new WindowMain(driver, number);
    }

    @Step
    public void callSubscriber(String number){
        windowMain = dialSubscriber(number);
        if(windowMain.isElementCallSubscriber()) actions.doubleClick(windowMain.getElementCallSubscriber()).build().perform();
    }

    @Step
    public boolean isButtonNoAnswerCall(){
        return new HelperClass(imgButtonNoAnswerCall).isGetPattern();
    }

    @Step
    public void clickNoAnswerCall(){ new HelperClass(imgButtonNoAnswerCall).clickPattern(); }

    @Step
    public boolean isButtonAnswerCall(){
        return new HelperClass(imgButtonAnswerCall).isGetPattern();
    }

    @Step
    public void clickAnswerCall(){
        new HelperClass(imgButtonAnswerCall).clickPattern();
    }

    @Step
    public boolean isButtonOpenMissedWindow(){
        return new HelperClass(imgButtonOpenMissedWindow).isGetPattern();
    }

    @Step
    public void clickButtonOpenMissedWindow(){
        new HelperClass(imgButtonOpenMissedWindow).clickPattern();
    }

    /*public boolean isWaitWindowAnswerCall(){
        return isWaitAttributeElement(driver.switchTo().activeElement(), "ClassName", "TrfmAlert", 60);
    }*/
}
