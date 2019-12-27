package ru.minicom.itphone.client;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.open;

public class TestRemoteGrid {

    @Test
    void StartSeleniumGrid() throws MalformedURLException {
        FirefoxOptions firefoxOptions;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(Platform.LINUX);
        capabilities.setBrowserName("firefox");

        URL url = new URL("http://localhost:4441/wd/hub");
        WebDriver driver = new RemoteWebDriver(url, capabilities);

        driver.get("https://yandex.ru");
        System.out.println("Blog title is " + driver.getTitle());
        driver.close();
    }

}
