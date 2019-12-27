package ru.minicom.itphone.client;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;

@Epic(value = "Тестирование главного окна")
@DisplayName(value = "Тестирование главного окна")
@ExtendWith(ResourcesTests.class)
public class Test4WindowMain extends HelperClass{

    private static ResourcesTests resourcesTests = new ResourcesTests();
    private static String login = resourcesTests.getSipSubscribe_A();
    private WindowCalls windowCalls = resourcesTests.getWindowCalls();
    private WindowMain windowMain = resourcesTests.getWindowMain(login);
    private static WindowSearchContacts windowSearchContacts = resourcesTests.getWindowSearchContacts();
    private String nameWindowCalls = "Все вызовы";

    @AfterEach
    void tearDown() {
        attachVideo();
        new HelperClass().exitWindowThroughEsc();
        windowMain.turnMainWindow();
        assertTrue( ! windowMain.isMainWindow(), "Не удалось свернуть главное окно");
    }

    /********************************** ТЕСТИРОВАНИЕ ВЫЗОВА ГЛАВНОГО ОКНА **********************************/

    @Feature(value = "Вызов окна")
    @Story(value = "Вызов главного окна через горячие кнопки")
    @DisplayName(value = "Вызов главного окна через горячие клавиши Ctrl+Shift+F1...")
    @Description(value = "Вызов главного окна через горячие клавиши Ctrl+Shift+F1...")
    @AnnotationTests
    void test_Call_Main_Window_Hot_Buttons() {
        assertTimeout(ofSeconds(300), () ->{
        windowMain.keyPressHotButtons();
        assertTrue(windowMain.isMainWindow(), "Вызов главного окна через горячие клавиши Ctrl+Shift+F1 не работет");
        }, () -> "Тест выполняется больше 5 минут");
    }

    @Feature(value = "Вызов окна")
    @Story(value = "Вызов главного окна через иконку в tree")
    @DisplayName(value = "Вызов главного окна через иконку в трее...")
    @Description(value = "Вызов главного окна через иконку в трее...")
    @AnnotationTests
    void test_Call_Main_Window_Icon_Tree() {
        assertTimeout(ofSeconds(300), () ->{
            windowMain.getIconClientPanelTree().click();
            assertTrue(windowMain.isMainWindow(), "Вызов главного окна через иконку в трее не работает");
        }, () -> "Тест выполняется больше 5 минут");
    }

    /********************************** ТЕСТИРОВАНИЕ КНОПОК НА ГЛАВНОМ ОКНЕ **********************************/

    @Nested
    @Epic(value = "Тестирование главного окна")
    @Feature(value = "Тестирование кнопок на главном окне")
    @DisplayName(value = "Тестирование кнопок на главном окне")
    class TestButtons{

        @Story(value = "Вызов главного меню")
        @DisplayName(value = "Вызов главного меню через конпку на главном окне")
        @Description(value = "Вызов окна 'Поиск контактов' через кнопку на главном окне...")
        @AnnotationTests
        void test_Call_Main_Menu() {
            assertTimeout(ofSeconds(300), () ->{
                windowMain.getIconClientPanelTree().click();
                windowMain.clickButtonMainMenu();
                assertTrue(windowMain.isAvailableMainMenu(), "Кнопка вызова главного меню не работает");
            }, () -> "Тест выполняется больше 5 минут");
        }

        @Story(value = "Вызов окна 'Поиск контактов'")
        @DisplayName(value = "Вызов окна 'Поиск контактов' через кнопку на главном окне...")
        @Description(value = "Вызов окна 'Поиск контактов' через кнопку на главном окне...")
        @AnnotationTests
        void test_Call_Window_Search_Contacts() {
            assertTimeout(ofSeconds(300), () ->{
                windowMain.getIconClientPanelTree().click();
                windowSearchContacts = windowMain.clickSearchWindow();
                assertTrue(windowSearchContacts.isAvailableWindowSearchContacts(), "Кнопка вызова окна 'Поиск контактов' не работает");
            }, () -> "Тест выполняется больше 5 минут");
        }

        @Story(value = "Вызов окна 'Все вызовы'")
        @DisplayName(value = "Вызов окна 'Последние вызовы' через кнопку на главном окне...")
        @Description(value = "Вызов окна 'Последние вызовы' через кнопку на главном окне...")
        @AnnotationTests
        void test_Call_Window_Late_Calls() throws InterruptedException {
            assertTimeout(ofSeconds(300), () ->{
                windowMain.getIconClientPanelTree().click();
                windowCalls = windowMain.clickLateCallsWindow();
                assertTrue(windowCalls.isCheckNameWindow(nameWindowCalls), "Кнопка вызова окна 'Все вызовы' не работает");
            }, () -> "Тест выполняется больше 5 минут");
        }
    }
}