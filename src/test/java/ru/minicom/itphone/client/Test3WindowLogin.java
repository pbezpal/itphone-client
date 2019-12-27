package ru.minicom.itphone.client;

import io.qameta.allure.*;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static java.time.Duration.ofSeconds;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeout;


@Epic(value = "Тестирование окна логина")
@Feature(value = "Параметрические тесты")
@DisplayName(value = "Параметрические тесты")
@ExtendWith(ResourcesTests.class)
public class Test3WindowLogin extends HelperClass {

    private static String str = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ЂЃ‚ѓ„…†‡€‰Љ‹ЊЌЋЏђ‘’“”•–—™љ›њќћџ ЎўЈ¤Ґ¦§Ё©Є«¬®Ї°±Ііґµ¶·ё№є»јЅѕїАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяƒˆŠŒŽ˜šœžŸ¡¢£¥¨ª¯²³´¸¹º¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
    //private static String str = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~‘’“”—АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяƒ";
    //private static final String str = "!";
    private static boolean window;
    private static WindowLogin windowLogin = new ResourcesTests().getWindowLogin();
    private static String login = new DataSubscribers().getSubscriberSIP_A();
    private static String wrongLogin = "ahsdgjaygkdgh";
    private static String password = new DataSubscribers().getPasSubscribers();
    private static String wrongPassword = "jkashdjkahdkhaj";

    @AfterEach
    void tearDown(){
        attachVideo();
    }

    /********************************** ПАРАМЕТРИЗИРОВАННЫЕ ТЕСТЫ **********************************/

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Character> Symbols() {
        ArrayList<Character> data = new ArrayList<>();

        for (char c : str.toCharArray()) data.add(c);

        return data;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<String> Text() {
        ArrayList<String> data = new ArrayList<>();
        String tempStr = "";

        for (char c : str.toCharArray()) {
            tempStr = tempStr + c;
            data.add(tempStr);
        }

        return data;
    }

    @Story(value = "Тестирование ввода любых символов в поле логин")
    @DisplayName(value = "Тест на ввод символов в поле логина...")
    @Description(value = "Тестирование ввода любых символов в поле логин")
    @ParameterizedTests
    @MethodSource("Symbols")
    void test_Symbols_Login(char symbol) {
        assertTimeout(ofSeconds(120), () -> {
            windowLogin.setLogin(Character.toString(symbol));
            char[] rez = windowLogin.getLogin().toCharArray();
            assertTrue(symbol == rez[0]);
        }, () -> "Тест выполняется больше 2 минуты");
    }

    @Story(value = "Тестирование ввода любых символов в поле сервер")
    @DisplayName(value = "Тест на ввод символов в поле логина...")
    @Description(value = "Тестирование ввода любых символов в поле сервер")
    @ParameterizedTests
    @MethodSource("Symbols")
    void test_Symbols_Server(char symbol) {
        assertTimeout(ofSeconds(120), () -> {
            windowLogin.setServer(Character.toString(symbol));
            char[] rez = windowLogin.getServer().toCharArray();
            assertTrue(symbol == rez[0]);
        }, () -> "Тест выполняется больше 2 минут");
    }

    @Story(value = "Тестирование возможности ввода логина любой длины до 32 символов")
    @DisplayName(value = "Тест на длину логина разной длины до 32 символов...")
    @Description(value = "Тестирование возможности ввода логина любой длины до 32 символов")
    @ParameterizedTests
    @MethodSource("Text")
    void test_Length_Login(String text) {
        assertTimeout(ofSeconds(120), () -> {
            windowLogin.setLogin(text);
            String tmpStr = windowLogin.getLogin();
            assertTrue(!tmpStr.isEmpty() && tmpStr.length() <= 32);
        }, () -> "Тест выполняется больше 2 минут");
    }

    /********************************** ТЕСТИРОВАНИЕ РАЗЛИЧНЫХ ОКОН **********************************/

    @Nested
    @Epic(value = "Тестирование окна логина")
    @Feature(value = "Тестирование разных окон")
    @DisplayName(value = "Тестирование разных окон")
    class TestsDifferentWindows{

        @Story(value = "Тестирование отмены подтверждения выхода из программы")
        @Description(value = "Отмена подтверждения выхода из программы")
        @DisplayName(value = "Отмена подтверждения выхода из программы...")
        @AnnotationWindowLogin
        void test_Not_Exit_Auth_Window() {
            assertTimeout(ofSeconds(120), () ->{
                windowLogin.clickNoExitConfirmForm();
                window = windowLogin.isAvailableLoginWindow();
                assertTrue("Окно авторизации закрывается при отмене выхода", window);
            }, () -> "Тест выполняется больше 2 минут");
        }

        @Story(value = "Тестирование выбора сервера из окна поиска сервера")
        @Description(value = "Тестирование выбора сервера из окна поиска сервера")
        @DisplayName(value = "Тестирование выбора сервера из окна поиска сервера...")
        @AnnotationWindowLogin
        void test_Choice_Server_In_Window_Look_Servers() {
            assertTimeout(ofSeconds(120), () ->{
                windowLogin.clearServer();
                windowLogin.clickButtonLookServers();
                assertTrue("Не работает кнопка вызова окна поиска сервера", windowLogin.isAvailableWindowLookServers());
                windowLogin.clickServer();
                windowLogin.clickButtonOkWindowLookServers();
                assertTrue("Сервер не выбран. Поле сервера пустое.",
                        ! windowLogin.getServer().isEmpty() && ! windowLogin.getServer().equals(":0"));
            }, () -> "Тест выполняется больше 2 минут");
        }

    }

    /********************************** АВТОРИЗАЦИЯ С НЕКОРРЕКТНЫМИ ДАННЫМИ **********************************/

    @Nested
    @Epic(value = "Тестирование окна логина")
    @Feature(value = "Тетирование авторизации с некооректными данными")
    @DisplayName("Авторизация на сервере с некорректными данными")
    class TestsInvalidCreds{

        @Story(value = "Авторизации на сервере с некорректным логином и корректным паролем")
        @Description(value = "Авторизации на сервере с некорректным логином и корректным паролем")
        @DisplayName(value = "Проверка на некорректный логин...")
        @AnnotationWindowLogin
        void test_Wrong_Login() {
            assertTimeout(ofSeconds(120), () ->{
                windowLogin.setDataFields(wrongLogin, password, "172.22.50.100");
                windowLogin.clickEnterButton();
                window = windowLogin.isAvailableWindowWithInvalidCreds();
                windowLogin.clickButtonWindowInvalidCreds();
                assertTrue("Успешная авторизация на сервере с некорректным логином", window);
            }, () -> "Тест выполняется больше 2 минут");
        }

        @Story(value = "Тестирование авторизации на сервере с корректным логином и некорректным паролем")
        @Description(value = "Тестирование авторизации на сервере с корректным логином и некорректным паролем")
        @DisplayName(value = "Проверка на некорректный пароль...")
        @AnnotationWindowLogin
        void test_Wrong_Password() {
            assertTimeout(ofSeconds(120), () ->{
                windowLogin.setDataFields(login, wrongPassword, "172.22.50.100");
                windowLogin.clickEnterButton();
                window = windowLogin.isAvailableWindowWithInvalidCreds();
                windowLogin.clickButtonWindowInvalidCreds();
                assertTrue("Успешная авторизация на сервере с некорректным паролем", window);
            }, () -> "Тест выполняется больше 2 минут");
        }

        @Story(value = "Авторизации на сервере с некорректным адресом сервера")
        @Description(value = "Авторизации на сервере с некорректным адресом сервера")
        @DisplayName(value = "Введён некорректный сервер...")
        @AnnotationWindowLogin
        void test_Invalid_Server() {
            assertTimeout(ofSeconds(120), () ->{
                windowLogin.setDataFields(login, password, "1");
                windowLogin.clickEnterButton();
                window = windowLogin.isAvailableWindowInvalidServer();
                windowLogin.clickButtonWindowInwalidServer();
                assertTrue("Успешная авторизация на сервере с некорректным паролем", window);
            }, () -> "Тест выполняется больше 2 минут");
        }
    }
}
