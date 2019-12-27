package ru.minicom.itphone.client;

import org.openqa.selenium.WebDriver;

import java.io.File;

public interface RunTests {
    public Process RunWinum();
    public WebDriver startClient();
    public String getSipSubscribe_A();
    public String getSipSubscribe_B();
    public String getPassword();
    public String getServer();
    public void deleteFolderProfile(File file);
    public boolean pingServer();
}
