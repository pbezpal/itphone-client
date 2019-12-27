package ru.minicom.itphone.client;

import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import java.awt.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HelperClass{

    private Robot robot;
    private Screen screen = new Screen();
    private Pattern pattern;
    private Pattern element;
    private Point location = MouseInfo.getPointerInfo().getLocation();
    private WebDriver driver = null;

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public HelperClass(){}

    public HelperClass(String nameImg) {
        this.pattern = new Pattern(".\\src\\main\\resources\\images\\" + nameImg);
    }

    public HelperClass(Pattern element) { this.element = element; }

    public HelperClass(WebDriver driver) { this.driver = driver; }

    public void clickPattern(){
        if(isGetPattern()){
            screen.delayClick(1000);
            try {
                screen.click(this.element);
            } catch (FindFailed findFailed) {
                findFailed.printStackTrace();
            }
        }
    }

    public boolean isGetPattern(){
        if(screen.exists(this.element, 5000).isValid()) return true;
        else return false;
    }

    public Pattern getPattern(){
        return this.pattern;
    }

    @Step(value = "Нажимаем Esc")
    public void exitWindowThroughEsc() {
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.delay(1000);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        robot.delay(1000);
    }

    @Step("Перемещаем курсор на X={offsetX} и Y={offsetY}")
    public void mouseMove(double offsetX, double offsetY){
        int x = (int)(location.getX() - offsetX);
        int y = (int)(location.getY() - offsetY);
        robot.mouseMove(x, y);
    }

    @Step
    public WebElement isWaitElement(By locator, int timeWait, int type){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeWait);
            WebElement element = null;
            switch (type) {
                case 1:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator));
                    break;
                case 2:
                    element = wait.until((WebDriver d) -> d.findElement(locator));
                    break;
            }

            return element;
        }catch (TimeoutException timeout){
            return null;
        }
    }

    @Step
    public boolean isWaitElement(By locator, int timeWait, String value,  int type){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeWait);
            boolean element = false;
            switch (type) {
                case 1:
                    element = wait.until((WebDriver d) -> d.findElement(locator).getAttribute("ClassName").equals(value));
                    break;
                case 2:
                    element = wait.until((WebDriver d) -> d.findElement(locator).getAttribute("Name").equals(value));
                    break;
                case 3:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator).getAttribute("ClassName").equals(value));
                    break;
                case 4:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator).getAttribute("Name").equals(value));
                    break;
                case 5:
                    element = wait.until((WebDriver d) -> d.findElement(locator).isDisplayed());
                    break;
                case 6:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator).isDisplayed());
                    break;
                case 7:
                    element = wait.until((WebDriver d) -> d.findElement(locator).isEnabled());
                    break;
                case 8:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator).isEnabled());
                    break;
                case 9:
                    element = wait.until((WebDriver d) -> d.findElement(locator).isSelected());
                    break;
                case 10:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElement(locator).isSelected());
                    break;
                case 11:
                    element = wait.until(invisibilityOfElementLocated(locator));
                    break;
                case 12:
                    element = wait.until(invisibilityOf(driver.switchTo().activeElement().findElement(locator)));
                    break;
                case 13:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().findElements(locator).isEmpty());
                    break;
            }
            return element;
        }catch (TimeoutException timeout){
            return false;
        }
    }

    @Step
    public boolean isWaitElement(int timeWait, String value,  int type){
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeWait);
            boolean element = false;
            switch (type) {
                case 1:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().getAttribute("ClassName").equals(value));
                    break;
                case 2:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().getAttribute("Name").equals(value));
                    break;
                case 3:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().isDisplayed());
                    break;
                case 4:
                    element = wait.until((WebDriver d) -> d.switchTo().activeElement().isEnabled());
                    break;
            }
            return element;
        }catch (TimeoutException timeout){
            return false;
        }
    }

    @Step
    public boolean isWaitElement(WebElement element, int timeWait, int type){
        try{
            WebDriverWait wait = new WebDriverWait(driver, timeWait);
            switch (type){
                case 1:
                    wait.until(visibilityOf(element));
                    break;
                case 2:
                    wait.until(invisibilityOf(element));
                    break;
            }
            return true;
        }catch (TimeoutException time){
            return false;
        }
    }

    @Attachment(value = "{0}", type = "video/mp4")
    public byte[] attachVideo(){
        try{
            String tmpDir = ".\\Videos\\tmp";
            String workDir = ".\\Videos";
            File video = copyVideo(tmpDir, workDir);

            return Files.toByteArray(video);
        } catch (Exception ignored) {
            return null;
        }
    }

    public File copyVideo(String tmpDir, String workDir){
        File tmpFolder = new File(tmpDir);
        File tmpVideo;
        File workVideo = null;
        String[] listFiles = tmpFolder.list();

        for(String file : listFiles){
            tmpVideo = new File(tmpDir + "\\" + file);
            workVideo = new File(workDir + "\\" + file);
            try {
                FileUtils.copyFile(tmpVideo, workVideo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(workVideo.exists() && tmpVideo.length() == workVideo.length()){
                tmpVideo.delete();
            }
        }

        return workVideo;
    }
}
