package ru.minicom.itphone.client;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.io.IOException;
import java.net.*;

public class StartTest{

    private static DataSubscribers dataSubscribers = new DataSubscribers();
    private static String winumPath = ".\\src\\main\\resources\\drivers\\";
    private static String winumName = "Winium.Desktop.Driver.exe";
    private static ProcessBuilder winum;
    private static DesiredCapabilities cap;
    private static String sipSubscribe_A = dataSubscribers.getSubscriberSIP_A();
    private static String sipSubscribe_B = dataSubscribers.getSubscriberSIP_B();
    private static String password = dataSubscribers.getPasSubscribers();
    private static String server = dataSubscribers.getServer();

    public StartTest(){}

    @Step("Запускаем Winium.Desktop.Driver")
    public Process RunWinum() {
        winum = new ProcessBuilder(winumPath + winumName);
        try {
            return winum.start();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Step("Запускаем клиента IT-Phone")
    public WebDriver startClient() {
        cap = new DesiredCapabilities();
        //System.out.println("Инициализирован WebDriver на порту 9999");
        cap.setCapability("app", "C:\\Program Files (x86)\\SoftPhone\\CLIENT\\SoftPhoneClient.exe");
        //System.out.println("Клиент it-phone запущен");
        cap.setCapability("launchDelay", "5");

        try {
            return WebDriverPool.DEFAULT.getDriver(new URL("http://localhost:9999"), cap);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public String getSipSubscribe_A(){ return sipSubscribe_A; }

    public String getSipSubscribe_B(){ return sipSubscribe_B; }

    public void setSipSubscribe_A(String sipSubscribe_A){ this.sipSubscribe_A = sipSubscribe_A; }

    public void setSipSubscribe_B(String sipSubscribe_B){ this.sipSubscribe_B = sipSubscribe_B; }

    public String getPassword() { return password; }

    public String getServer() { return server; }

    @Step("Удаляем данные профиля пользователя, перед тестированием")
    public void deleteFolderProfile(File file){
        // до конца рекурсивного цикла
        if (!file.exists())
            return;
        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                deleteFolderProfile(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }

    @Step("Проверяем, доступен ли сервер {server}")
    public boolean pingServer() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(server);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        boolean reachable = false;
        try {
            reachable = address.isReachable(10000);
        } catch (IOException e) {
            e.printStackTrace();
            reachable = false;
        }
        return reachable;
    }
}
