package ru.minicom.itphone.client;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import ru.stqa.selenium.factory.WebDriverPool;

import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ResourcesTests implements BeforeAllCallback, AfterAllCallback {

    private static StartTest startTest = new StartTest();
    private static Process shell = null;
    private static WebDriver driver = null;
    private static WindowLogin windowLogin = null;
    private static WindowMain windowMain = null;
    private static WindowCalls windowCalls = null;
    private static WindowSearchContacts windowSearchContacts = null;
    private static Calls calls = null;

    private String profile = System.getProperty("user.home") + "\\AppData\\Roaming\\IT-Phone\\Profiles";;
    private String classTest = "";
    private String restartClient = "\"C:\\Program Files (x86)\\SoftPhone\\CLIENT\\SoftPhoneClient.exe\" -restart";
    private String noRestartClient = "\"C:\\Program Files (x86)\\SoftPhone\\CLIENT\\SoftPhoneClient.exe\"";
    private boolean window = false;

    private String sipSubscribe_A = startTest.getSipSubscribe_A();
    private String sipSubscribe_B = startTest.getSipSubscribe_B();
    private String password = startTest.getPassword();
    private String server = startTest.getServer();

    @Override
    public void beforeAll(@NotNull ExtensionContext context) {

        System.out.println("Class test: " + context.getTestClass());

        classTest = String.valueOf(context.getTestClass());

        assertTrue(startTest.pingServer(), "Севрер " + startTest.getServer() + " недоступен");

        shell = getProcess();

        if(classTest.contains("EmptyFieldsWindowLogin")) startTest.deleteFolderProfile(new File(profile));

        assertNotNull(shell, "Winium.Desktop.Driver не запущен...");

        driver = startTest.startClient();

        assertNotNull(driver, "WebDriver не запущен...");

        System.setProperty("video.folder", ".\\Videos\\tmp");
        System.setProperty("recorder.type", "FFMPEG");
        System.setProperty("video.save.mode", "FAILED_ONLY");

        calls = getCalls();

        windowLogin = getWindowLogin();

        if(classTest.contains("EmptyFieldsWindowLogin")) setPropertiesClient();

        if( ! classTest.contains("WindowLogin")) {
            System.out.println("SIP_A: " + sipSubscribe_A);
            System.out.println("Password: " + password);
            assertTrue( ! sipSubscribe_A.isEmpty(), "Абонента " + sipSubscribe_A + " не существует");
            enterClientServer(sipSubscribe_A, password, server);
        }else{

        }

        if(classTest.contains("Test5Calls")) {
            System.out.println("SIP_B: " + sipSubscribe_B);
            assertTrue( ! sipSubscribe_B.isEmpty(), "Абонента " + sipSubscribe_B + " не существует");
            calls = getCalls();
            calls.clickIconClient();
            enterClientServer(sipSubscribe_B, password, server);
        }

    }

    @Override
    public void afterAll(@NotNull ExtensionContext context){
        classTest = String.valueOf(context.getTestClass());

        if(classTest.contains("WindowSearchContacts")){

            windowMain.clickExitButton();
            windowMain = null;

            try {
                WebDriverPool.DEFAULT.dismissAll();
            } catch (WebDriverException e) {
                driver = null;
            }

            if (shell != null) shell.destroy();
        }
    }

    public static Process getProcess(){
        Date date = new Date();
        System.out.println("Время: " + date.toString() + " + shell: " + shell);
        if(shell == null) return startTest.RunWinum();
        else return shell;
    }

    public boolean isRestartClient(){
        calls.callPropertiesClient();
        calls.getElementTextObject().getText();
        return calls.getElementTextObject().getText().contains("restart");
    }

    public void setPropertiesClient(){
        if (!isRestartClient()) calls.setPropertiesClient(restartClient);
        calls.clickOkPropertiesClient();
    }

    public void enterClientServer(String login, String password, String server){

        windowLogin = getWindowLogin();
        windowMain = getWindowMain(login);

        if(windowLogin.isAvailableLoginWindow()) {

            if (!windowLogin.getLogin().equals(login)) {
                windowLogin.setLogin(login);
            }

            windowLogin.setPassword(password);

            if (!windowLogin.getServer().equals(server) && !windowLogin.getServer().equals(server + ":1215")) {
                windowLogin.setServer(server);
            }

            windowLogin.clickEnterButton();

            if(windowLogin.isAvailableWindowsSelectUserParams()){ windowLogin.clickUserParam();}

            window = windowLogin.isNotAvailableWindowWithInvalidCreds();
            if(windowLogin.isAvailableWindowWithInvalidCreds()) windowLogin.clickButtonWindowInvalidCreds();
            assertTrue(window, "Неверный логин или пароль");

            window = windowLogin.isNotAvailableWindowInvalidServer();
            if(windowLogin.isAvailableWindowInvalidServer()) windowLogin.clickButtonWindowInwalidServer();
            assertTrue(window, "Неправильный адрес сервера " + server + ", на сервере " + server + " закрыт порт 1215 или выключен сервер контактов");

            assertTrue(windowMain.isWaitNotVisibleLoginWindow(), "Неудачная авторизация на сервере");
        }
    }

    public WindowLogin getWindowLogin(){
        if(windowLogin == null) return new WindowLogin(driver);
        return windowLogin;
    }

    public WindowMain getWindowMain(String login){
        if(windowMain == null || ! windowMain.getLogin().equals(login)) return new WindowMain(driver, login);
        return windowMain;
    }

    public Calls getCalls(){
        if(calls == null) return new Calls(driver);
        return calls;
    }

    public WindowCalls getWindowCalls(){
        if(windowCalls == null) return new WindowCalls(driver, sipSubscribe_A);
        return windowCalls;
    }

    public WindowSearchContacts getWindowSearchContacts(){
        if(windowSearchContacts == null) return new WindowSearchContacts(driver, sipSubscribe_A);
        return windowSearchContacts;
    }

    public String getSipSubscribe_A(){
        return sipSubscribe_A;
    }

    public String getSipSubscribe_B(){
        return sipSubscribe_B;
    }
}
