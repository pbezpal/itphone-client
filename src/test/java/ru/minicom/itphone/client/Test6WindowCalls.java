package ru.minicom.itphone.client;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sikuli.script.Pattern;

import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Тестирование окна 'Все вызовы'")
@Feature(value = "Вызов окна")
@DisplayName(value = "Тестирование окна 'Все вызовы")
@ExtendWith(ResourcesTests.class)
public class Test6WindowCalls extends HelperClass{

    private static ResourcesTests resourcesTests = new ResourcesTests();
    private WindowCalls windowCalls = resourcesTests.getWindowCalls();
    private WindowMain windowMain = null;
    private String[] windows = windowCalls.getWindowsName();

    @AfterEach
    public void tearDown() {
        attachVideo();
        new HelperClass().exitWindowThroughEsc();
        assertTrue(windowCalls.isWaitNotVisibilityWindowCalls());
    }

    /********************************** ТЕСТИРОВАНИЕ ВЫЗОВА ОКНА ПОСЛЕДНИЕ ВЫЗОВЫ **********************************/

    @Story(value = "Вызова окна 'Последние вызовы' через горячие клавиши")
    @DisplayName(value = "Тестирование вызова окна 'Последние вызовы' через горячие клавиши ...")
    @Description(value = "Тестирование вызова окна 'Последние вызовы' через горячие клавиши ...")
    @AnnotationTests
    void test_Call_Window_Calls_Hot_Buttons() {
        windowCalls.callWindowCallsHotButtons();
        assertTrue(windowCalls.isCheckNameWindow(windows[0]));
    }

    @Story(value = "Вызов окна 'Последние вызовы' через главное меню")
    @DisplayName(value = "Тестирование вызова окна 'Последние вызовы' через главное меню ...")
    @Description(value = "Тестирование вызова окна 'Последние вызовы' через главное меню ...")
    @AnnotationTests
    void test_Call_Window_Calls_Main_Menu() {
        windowCalls.callWindowCallsMainMenu();
        assertTrue(windowCalls.isAvailableWindowCalls(), "Не работает вызов окна 'Последние вызовы' через главное меню");
    }

    /********************************** ТЕСТИРОВАНИЕ ПЕРЕКЛЮЧЕНИЯ ОКОН **********************************/

    @Nested
    @Epic(value = "Тестирование окна 'Все вызовы'")
    @Feature(value = "Переключение окон")
    @DisplayName(value = "Переключение окон")
    class ToRunOnWindows{

        @Story(value = "Переключение окон вправо")
        @DisplayName(value = "Тестируем переключение окон вправо...")
        @Description(value = "Тестируем переключение окон вправо...")
        @AnnotationTests
        void test_To_Run_To_The_Right_On_Window() {
            windowCalls.callWindowCallsMainMenu();
            assertTrue(windowCalls.isCheckNameWindow(windows[0]), "Окно " + windows[0] + " не появилось");
            for (int i = 0; i < windows.length - 1; i++) {
                assertTrue(windowCalls.isCheckNameWindow(windows[i]), "Ошибка переключения на окно " + windows[i]);
                windowCalls.toRunOnWindow(KeyEvent.VK_RIGHT);
            }
        }

        @Story(value = "Переключение окон влево")
        @DisplayName(value = "Тестируем переключение окон влево...")
        @Description(value = "Тестируем переключение окон влево...")
        @AnnotationTests
        void test_To_Run_To_The_Left_On_Window() {
            windowCalls.callWindowCallsMainMenu();
            assertTrue(windowCalls.isCheckNameWindow(windows[0]), "Окно " + windows[0] + " не появилось");
            for (int i = windows.length - 1; i > 0; i--) {
                windowCalls.toRunOnWindow(KeyEvent.VK_LEFT);
                assertTrue(windowCalls.isCheckNameWindow(windows[i]), "Ошибка переключения на окно " + windows[i]);
            }
        }
    }

    @Nested
    @Epic(value = "Тестирование окна 'Все вызовы'")
    @Feature(value = "Тестирование кнопок")
    @DisplayName(value = "Тестирование кнопок")
    class TestsButtons{
        @Story(value = "Тестирование кнопки для выхода из окна 'Последние вызовы'")
        @DisplayName(value = "Тестирование кнопки для выхода из окна 'Последние вызовы' ...")
        @Description(value = "Тестирование кнопки для выхода из окна 'Последние вызовы' ...")
        @AnnotationTests
        void test_Exit_Button_Call_Window_Calls() {
            windowCalls.callWindowCallsMainMenu();
            windowCalls.clickExitWindowCalls();
            assertTrue(windowCalls.isWaitNotVisibilityWindowCalls(), "Кнопка закрытия окна 'Последние вызовы' не работает");
        }

        @Story(value = "Переключение окон через кнопки")
        @DisplayName(value = "Тестируем переключение окон через кнопки'...")
        @Description(value = "Тестируем переключение окон через кнопки'...")
        @AnnotationTests
        void test_Click_Button_Windows_Calls() {
            windowCalls.callWindowCallsMainMenu();
            assertTrue(windowCalls.isCheckNameWindow(windows[0]), "Окно " + windows[0] + " не появилось");
            for (Map.Entry<String, Pattern> item : windowCalls.getButtonAndWindow().entrySet()) {
                new HelperClass(item.getValue()).clickPattern();
                assertTrue(windowCalls.isCheckNameWindow(item.getKey()), "Кнопка " + item.getValue() + " не работает");
            }
        }
    }

    @Nested
    @Epic(value = "Тестирование окна 'Все вызовы'")
    @Feature(value = "Тестирование вызова абонентов")
    @DisplayName(value = "Тестирование вызова абонентов")
    @TestMethodOrder(MethodOrderer.Alphanumeric.class)
    class TestCallsSubscribers{

        @Story(value = "Тестирование вызова абонентов из окна 'Все вызовы'")
        @DisplayName(value = "Тестирование вызова абонентов из окна 'Все вызовы' ...")
        @Description(value = "Тестирование вызова абонентов из разных окон в разделе 'Все вызовы' ...")
        @AnnotationTests
        void test_A_Calls_Subscribers() {
            windowCalls.callWindowCallsMainMenu();
            assertTrue(windowCalls.isCheckNameWindow(windows[0]), "Окно " + windows[0] + " не появилось");
            for (int i = windows.length - 1; i > 0; i--) {
                assertTrue(windowCalls.isElementCalls(), "Нет абонентов для вызова");
                windowMain = windowCalls.doubleClickElementCalls();
                boolean element = windowMain.isElementCallSubscriber();
                windowMain.clickButtonEndCall();
                windowMain.turnMainWindow();
                assertTrue(element, "Вызов контакта из окна " + windows[i] + " не работает");
                windowCalls.toRunOnWindow(KeyEvent.VK_LEFT);
            }
        }

        @Story(value = "Тестирование открытие окна 'Последние вызовы'")
        @DisplayName(value = "Тестирование открытие окна 'Последние вызовы' ...")
        @Description(value = "Тестирование открытие окна 'Последние вызовы' через форму пропущенных вызовов ...")
        @AnnotationTests
        void test_B_Call_Window_Missed_Calls(){
            assertTrue(windowCalls.isButtonOpenMissedWindow(), "Нет кнопки для открытия окна 'Последние вызовы' ...");
            windowCalls.clickButtonOpenMissedWindow();
            assertTrue(windowCalls.isElementCalls(), "Вызов окна 'Последние вызовы' через окно пропущенные вызовы не работает");
        }

    }

    @Nested
    @Epic(value = "Тестирование окна 'Все вызовы'")
    @Feature(value = "Тестирование контестного меню")
    @DisplayName(value = "Тестирование контестного меню")
    class TestContextMenu{

        @Story(value = "Тестирование вызова абонента через контекстное меню")
        @DisplayName(value = "Тестирование вызова абонента через контекстное меню ...")
        @Description(value = "Нажимаем на абонента правой кнопкой мыши, открываем контекстное меню и вызываем абонента ...")
        @AnnotationTests
        void test_C_Call_Subscriber_From_Context_Menu(){
            windowCalls.callWindowCallsMainMenu();
            assertTrue(windowCalls.isCheckNameWindow(windows[0]), "Окно " + windows[0] + " не появилось");
            windowMain = windowCalls.clickItemsCallSubscriber();
            try{
                windowCalls.isCheckNameWindow(windows[0]);
                windowCalls.clickExitWindowCalls();
            }catch (NoSuchElementException e){}
            boolean element = windowMain.isElementCallSubscriber();
            windowMain.clickButtonEndCall();
            windowMain.turnMainWindow();
            assertTrue(element, "Вызов контакта из контекстного не работает");
        }
    }
}
