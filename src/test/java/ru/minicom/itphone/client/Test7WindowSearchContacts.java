package ru.minicom.itphone.client;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic(value = "Тестирование окна 'Поиск контактов'")
@DisplayName(value = "Тестирование окна 'Поиск контактов'")
@ExtendWith(ResourcesTests.class)
public class Test7WindowSearchContacts extends HelperClass {


    private ResourcesTests resourcesTests = new ResourcesTests();
    private String login = resourcesTests.getSipSubscribe_A();
    private WindowMain windowMain = resourcesTests.getWindowMain(login);
    private WindowSearchContacts windowSearchContacts = resourcesTests.getWindowSearchContacts();

    @AfterEach
    public void tearDown() {
        attachVideo();
        exitWindowThroughEsc();
        assertTrue(windowSearchContacts.isWaitNotVisibilityWindowContacts());
    }

    /********************************** ТЕСТИРОВАНИЕ ВЫЗОВА ОКНА ПОИСК КОНТАКТОВ **********************************/

    @Feature(value = "Вызов окна")
    @Story(value = "Вызова окна через дополнительную иконку")
    @DisplayName(value = "Тестирование вызова окна поиска контактов через дополнительную иконку ...")
    @Description(value = "Тестирование вызова окна поиска контактов через дополнительную иконку ...")
    @AnnotationTests
    public void test_Call_Window_Search_Contacts_Through_Additional_Icon() {
        windowSearchContacts.clickAdditionalIconTree();
        mouseMove(100.0, 80.0);
        assertTrue(windowSearchContacts.isAvailableWindowSearchContacts(), "Окно 'Поиск контактов' не найдено");
    }

    @Feature(value = "Вызов окна")
    @Story(value = "Вызов окна через горячие клавиши")
    @DisplayName(value = "Тестирование вызова окна поиска контактов через горячие клавиши ...")
    @Description(value = "Тестирование вызова окна поиска контактов через горячие клавиши ...")
    @AnnotationTests
    public void test_Call_Window_Search_Contacts_Hot_Buttons() {
        windowSearchContacts.callWindowSearchContactsHotButtons();
        assertTrue(windowSearchContacts.isAvailableWindowSearchContacts(), "Окно 'Поиск контактов' не найдено");
    }

    @Feature(value = "Вызов окна")
    @Story(value = "Вызов окна через главное меню")
    @DisplayName(value = "Тестирование вызова окна поиска контактов через главное меню ...")
    @Description(value = "Тестирование вызова окна поиска контактов через главное меню ...")
    @AnnotationTests
    public void test_Call_Window_Search_Contacts_Through_Main_Menu() throws InterruptedException {
        windowSearchContacts.callWindowSearchContactsMainMenu();
        assertTrue(windowSearchContacts.isAvailableWindowSearchContacts(), "Окно 'Поиск контактов' не найдено");
    }

    /********************************** ТЕСТИРОВАНИЕ ПОИСКА КОНТАКТОВ **********************************/

    @Feature("Поиск контактов")
    @Story("Поиск контактов")
    @DisplayName("Тестирование поиска контактов ...")
    @Description("Тестирование поиска контактов ...")
    @AnnotationTests
    public void test_Search_Contacts() {
        windowSearchContacts.callWindowSearchContactsMainMenu();
        windowSearchContacts.searchContacts("5");
        assertTrue(windowSearchContacts.isElementFindContacts(), "Ни один контакт не найден или поиск контактов не работает");
    }

    @Feature("Поиск контактов")
    @Story("Вызов найденного контакта")
    @DisplayName("Тестирование вызова найденного контакта ...")
    @Description("Тестирование вызова найденного контакта ...")
    @AnnotationTests
    public void test_Call_Find_Contact() {
        windowSearchContacts.callWindowSearchContactsMainMenu();
        windowSearchContacts.searchContacts("5099");
        assertTrue(windowSearchContacts.isElementFindContacts(), "Ни один контакт не найден или поиск контактов не работает");
        windowMain = windowSearchContacts.doubleClickFindContact();
        boolean element = windowMain.isElementCallSubscriber();
        windowMain.clickButtonEndCall();
        windowMain.turnMainWindow();
        assertTrue(element, "Вызов контакта из окна 'Поиск контаков' не работает");
    }
}
