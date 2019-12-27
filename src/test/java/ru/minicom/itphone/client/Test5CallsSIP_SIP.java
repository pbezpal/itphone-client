package ru.minicom.itphone.client;


import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Тестирование вызовов")
@Feature(value = "Вызовы Sip to Sip")
@DisplayName(value = "Вызовы Sip to Sip")
@ExtendWith(ResourcesTests.class)
public class Test5CallsSIP_SIP extends HelperClass{

    private static ResourcesTests resourcesTests = new ResourcesTests();
    private static String login = resourcesTests.getSipSubscribe_B();
    private static Calls calls = resourcesTests.getCalls();
    private static WindowMain windowMain = resourcesTests.getWindowMain(login);
    private static WindowCalls windowCalls = resourcesTests.getWindowCalls();

    @AfterEach
    void tearDown(){
        attachVideo();
        exitWindowThroughEsc();
    }

    /*@BeforeEach
    void seatUp(){
        try{
            windowMain.isMainWindow();
            windowMain.turnMainWindow();
        }catch (NoSuchElementException e){}
    }*/

    @Story(value = "Звонок SIP->SIP. Ответ")
    @Description(value = "Абонент B звонит абоненту A. Абонент А отвечает на вызов")
    @DisplayName(value = "Абонент B звонит абоненту A. Абонент А отвечает на вызов")
    @AnnotationTests
    void test_Answer_Call() {
        windowMain.getIconClientPanelTree().click();
        assertTrue(windowMain.isMainWindow(), "Вызов главного окна через иконку в трее не работает");
        calls.callSubscriber(login);
        windowMain.turnMainWindow();
        assertTrue(calls.isButtonAnswerCall(), "Кнопка ответа на вызов отсутствует");
        calls.clickAnswerCall();
        boolean element = windowMain.isElementCallSubscriber();
        windowMain.clickButtonEndCall();
        windowMain.turnMainWindow();
        assertTrue(element, "Не работает кнопка ответа на вызов у SIP абонента");
    }

    @Story(value = "Звонок SIP->SIP. Отбой аббонента B")
    @Description(value = "Абонент B звонит абоненту A. Абонент A отбивает вызов")
    @DisplayName(value = "Абонент B звонит абоненту A. Абонент A отбивает вызов")
    @AnnotationTests
    void test_Answer_Call_Cancel_Subscriber_A() {
        windowMain.getIconClientPanelTree().click();
        assertTrue(windowMain.isMainWindow(), "Вызов главного окна через иконку в трее не работает");
        calls.callSubscriber(login);
        windowMain.turnMainWindow();
        assertTrue(calls.isButtonNoAnswerCall(), "Кнопка отбоя вызова отсутствует");
        calls.clickNoAnswerCall();
        assertTrue(windowMain.isNotElementCallSubscriber(), "Почему-то вызов был принят");
    }

    @Story(value = "Звонок SIP->SIP. Отбой аббонента B")
    @Description(value = "Абонент B звонит абоненту A. Абонент B отбивает вызов")
    @DisplayName(value = "Абонент B звонит абоненту A. Абонент B отбивает вызов")
    @AnnotationTests
    void test_Answer_Call_Cancel_Subscriber_B() {
        windowMain.getIconClientPanelTree().click();
        assertTrue(windowMain.isMainWindow(), "Вызов главного окна через иконку в трее не работает");
        calls.callSubscriber(login);
        windowMain.clickButtonEndCall();
        windowCalls.clickButtonOpenMissedWindow();
        windowCalls.clickExitWindowCalls();
        assertTrue(windowMain.isNotElementCallSubscriber(), "Почему-то вызов был принят");
        windowMain.turnMainWindow();
    }
}
