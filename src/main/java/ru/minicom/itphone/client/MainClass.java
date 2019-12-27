package ru.minicom.itphone.client;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.*;
import java.nio.file.Files;

public class MainClass {

    private static String str;

    public static void main(String[] args) throws IOException {

        if(str == null) {
            str = "Abra kadabra";
            System.out.println(str);
        }
        /*    File folderVideo = new File(".\\Videos\\tmp" );
        File tmpVideo;
        File workVideo;
        String[] listFile = folderVideo.list();

        for(String s: listFile){
            tmpVideo = new File(".\\Videos\\tmp\\" + s);
            workVideo = new File(".\\Videos\\" + s);
            FileUtils.copyFile(tmpVideo, workVideo);
            if(workVideo.exists() && tmpVideo.length() == workVideo.length()){
                tmpVideo.delete();
            }
        }*/



    }

    /*private static StartTest startTest = new StartTest();
    private static Actions actions;

    public static void main(String[] args){
        Process shell = startTest.RunWinum();
        WebDriver driver = startTest.startClient();

        actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.name("IT-Phone"))).perform();
        driver.findElement(By.className("#32768")).findElement(By.name("Свойства")).click();
        if(!driver.findElements(By.className("Edit")).get(1).getText().contains("restart")){
            driver.findElement(By.name("Объект:")).clear();
            driver.findElement(By.name("Объект:")).sendKeys("\"C:\\Program Files (x86)\\SoftPhone\\CLIENT\\SoftPhoneClient.exe\" -restart");
            driver.findElement(By.name("ОК")).clear();
            driver.findElement(By.name("Продолжить")).clear();
        }else driver.findElement(By.name("Отмена")).clear();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        actions.doubleClick(driver.findElement(By.name("IT-Phone"))).perform();

        WebDriverPool.DEFAULT.dismissAll();
        shell.destroy();
    }*/

}
