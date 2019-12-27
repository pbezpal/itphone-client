package ru.minicom.itphone.client;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.close;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;
import static org.junit.gen5.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@Epic(value = "Окно авторизации")
@Feature(value = "Авторизация на сервере")
@DisplayName(value = "Авторизация на сервере")
public class Test1ExistsContacts extends WebForm {

    private StartTest startTest = new StartTest();
    private DataSubscribers dataSubscribers= new DataSubscribers();
    private String sipSubscriber_A = dataSubscribers.getSubscriberSIP_A();
    private String sipSubscriber_B = dataSubscribers.getSubscriberSIP_B();
    private String portDx_Subscribers_A = dataSubscribers.genPortDx_Subscribers_A();
    private String portDx_Subscribers_B = dataSubscribers.genPortDx_Subscribers_B();

    @BeforeAll
    static void beforeTest(){
        System.setProperty("video.folder", ".\\Videos\\tmp");
        System.setProperty("recorder.type", "FFMPEG");
        System.setProperty("video.save.mode", "FAILED_ONLY");
        System.setProperty("selenide.browser", "firefox");
        open("https://172.22.50.100:40443");
        loginOnServer();
        clickSubscribers();
    }

    @AfterEach
    void tearDown(){ attachVideo(); }

    @AfterAll
    static void afterClass(){
        close();
    }

    @Story(value = "Добавляем SIP абонента А")
    @Description(value = "Проверяем и добавляем SIP абонентов A для тестирования клиента")
    @DisplayName(value = "Проверяем и добавляем SIP абонентов A для тестирования клиента")
    @AnnotationTests
    void Test_Add_Subscriber_A(){
        assertTimeout(ofSeconds(120), () -> {
            setSubscribersSIP(sipSubscriber_A, portDx_Subscribers_A);
            assertTrue(checkSubscribersSIP(sipSubscriber_A) > 0, "Абонент " + sipSubscriber_A + " не был добавлен");
            startTest.setSipSubscribe_A(sipSubscriber_A);
        }, () -> "Тест выполняется больше 2 минут");
    }

    @Story(value = "Добавляем SIP абонента B")
    @Description(value = "Проверяем и добавляем SIP абонентов B для тестирования клиента")
    @DisplayName(value = "Проверяем и добавляем SIP абонентов B для тестирования клиента")
    @AnnotationTests
    void Test_Add_Subscriber_B(){
        assertTimeout(ofSeconds(120), () -> {
            setSubscribersSIP(sipSubscriber_B, portDx_Subscribers_B);
            assertTrue(checkSubscribersSIP(sipSubscriber_B) > 0, "Абонент " + sipSubscriber_B + " не был добавлен");
            startTest.setSipSubscribe_B(sipSubscriber_B);
        }, () -> "Тест выполняется больше 2 минут");
    }
}
