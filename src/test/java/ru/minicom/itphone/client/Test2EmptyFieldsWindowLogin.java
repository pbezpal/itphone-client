package ru.minicom.itphone.client;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.time.Duration.ofSeconds;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;

@Epic(value = "Тестирование окна логина")
@Feature(value = "Тестирование пустых полей")
@DisplayName(value = "Тестирование пустых полей")
@ExtendWith(ResourcesTests.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test2EmptyFieldsWindowLogin extends HelperClass{

    private static boolean window;
    private static WindowLogin windowLogin = new ResourcesTests().getWindowLogin();

    @AfterEach
    void tearDown(){
        attachVideo();
    }

    /********************************** ТЕСТИРОВАНИЕ ПУСТЫХ ПОЛЕЙ **********************************/

    @Story(value = "Все поля пустые")
    @Description(value = "Не заполнено ниодно поле")
    @DisplayName(value = "Не заполнено ниодно поле...")
    @AnnotationWindowLogin
    @Order(0)
    void test_Empty_All_Field() {
        assertTimeout(ofSeconds(120), () ->{
            assertTrue("Кнопка войти активна!", windowLogin.isInactiveButtonEnter().equals("False"));
        }, () -> "Тест выполняется больше 2 минут");
    }

    @Story(value = "Поле сервер не заполнено")
    @Description(value = "Если поле сервер не заполнено, тогда должно появиться окно с ошибкой")
    @DisplayName(value = "Поле сервер не заполнено...")
    @AnnotationWindowLogin
    @Order(2)
    void test_Empty_Field_Server() {
        assertTimeout(ofSeconds(120), () ->{
            windowLogin.setDataFields("2002", "o0cJ2uas");
            windowLogin.clickEnterButton();
            window = windowLogin.isAvailableWindowEmptyServer();
            windowLogin.clickButtonWindowEmptyServer();
            assertTrue("Окно с ошибкой не появилось", window);
        }, () -> "Тест выполняется больше 2 минут");
    }

    @Story(value = "Поле логина не заполнено")
    @Description(value = "Если поле логина пустое, кнопка Войти должна быть неактивной")
    @DisplayName(value = "Поле сервер не заполнено...")
    @AnnotationWindowLogin
    @Order(3)
    void testEmptyFieldLogin() {
        assertTimeout(ofSeconds(120), () ->{
            windowLogin.clearLogin();
            windowLogin.setDataFields("", "o0cJ2uas", "172.22.50.100");
            assertTrue("Кнопка войти активна!", windowLogin.isInactiveButtonEnter().equals("False"));
        }, () -> "Тест выполняется больше 2 минут");
    }

    @Story(value = "Поле пароль не заполнено")
    @Description(value = "Если поле пароль пустое, кнопка Войти должна быть неактивной")
    @DisplayName(value = "Поле пароль не заполнено...")
    @AnnotationWindowLogin
    @Order(4)
    void test_Empty_Field_Password() {
        assertTimeout(ofSeconds(120), () ->{
            windowLogin.clearPassword();
            windowLogin.setDataFields("2002", "", "172.22.50.100");
            assertTrue("Кнопка войти активна!", windowLogin.isInactiveButtonEnter().equals("False"));
        }, () -> "Тест выполняется больше 2 минут");
    }
}
