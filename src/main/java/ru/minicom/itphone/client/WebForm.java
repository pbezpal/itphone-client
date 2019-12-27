package ru.minicom.itphone.client;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.gen5.api.Assertions.assertTrue;

public class WebForm extends HelperClass{

    /************ Объявляем переменные *****************/
    private static DataSubscribers dataSubscribers = new DataSubscribers();
    private static String login = dataSubscribers.getLoginSubscribers();
    private static String password = dataSubscribers.getPasSubscribers();
    private static String passwordWeb = dataSubscribers.getPasswordWeb();
    private static String server = dataSubscribers.getServer();
    private static String portNotify = dataSubscribers.getPortNotify();
    private static String portSip = dataSubscribers.getPortSip();

    private static By nameBy = By.id("name");
    private static By passwordBy = By.id("password");
    private static By buttonBy = By.className("ui-button-text");
    private static By formLoginBy = By.id("dialog-authentication-form");
    private static By tableSystemInfoBy = By.xpath("//table[@id='text-info']//td[text()='сетевые интерфейсы:']");
    private static By svOpensipsBy = By.xpath("//div[@id='sv-opensips']/div/img[@src=('icons/green-icon.png' or 'icons/yellow-icon.png')]");
    private static By gwSipDx = By.xpath("//div[@id='gw-sip-dx']/div/img[@src=('icons/green-icon.png' or 'icons/yellow-icon.png')]");
    private static By linkSubscribersBy = By.xpath("//a[@title='Справочник абонентов']");
    private static By tableSubscribersBy = By.xpath("//table[@id='list_users']/tbody/tr[@role='row']");

    private By searchSubscribersBy = By.xpath("//input[@type='search']");
    private By buttonAddSubscriberBy = By.xpath("//button[@id='add_subscriber']");
    private By sipUserParamsTriggerBy = By.xpath("//i[@id='sp_user_params_trigger']");
    private By spUserNameBy = By.xpath("//input[@id='sp_user_name']");
    private By userNsIpBy = By.xpath("//input[@name='user_ns_ip']");
    private By userNsPortBy = By.xpath("//input[@name='user_ns_port']");
    private By spUserPasswordBy = By.xpath("//input[@id='sp_user_password']");
    private By spUserPwdConfirmBy = By.xpath("//input[@id='sp_user_pwd_confirm']");
    private By sipSpWrapHeaderBy = By.xpath("//span[text()='SIP']//ancestor::div[@class='sp-wrap-header']/i");
    private String locatorSip = "//span[text()='SIP']//ancestor::fieldset[contains(@class,'dialog sp-wrap-label params-set set-type')]";
    private By spanServIpBy = By.xpath(locatorSip + "//input[@name='serv_ip']");
    private By spanServPortBy = By.xpath(locatorSip + "//input[@name='serv_port']");
    private By spanSpSipPortBy = By.xpath(locatorSip + "//input[@name='port1']");
    private By spanNumberListBy = By.xpath(locatorSip + "//input[@name='number_list']");
    private By phoneBy = By.xpath("//input[@name='phone']");
    private By lastNameBy = By.xpath("//input[@placeholder='Фамилия']");
    private By buttonSaveBy = By.xpath("//button[@class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']/span[text()='Сохранить']");
    private By buttonNotEmailBy = By.xpath("//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-dialog-buttons ui-draggable ui-resizable']//span[text()='Нет']");

    @Step(value = "Логинимся на сервер")
    public static void loginOnServer(){
        try{
            $(formLoginBy).isEnabled();
            $(nameBy).sendKeys(login);
            $(passwordBy).sendKeys( passwordWeb );
            $(buttonBy).click();
            SelenideElement elementTitle = $("h2").shouldHave(text("Система управления специализированным сервером"));
            assertTrue(elementTitle.exists(), "Не удалось войти в систему");
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    @Step(value = "Проверяем, настроен ли SIP сервер")
    public static void clickSubscribers(){
        try{
            $(tableSystemInfoBy).waitUntil(visible, 30000).isEnabled();
            assertTrue($(svOpensipsBy).isEnabled(), "Не настроен SIP сервер");
            assertTrue($(gwSipDx).isEnabled(), "Нет соединения SIP сервера со станцией DX");
            $(linkSubscribersBy).shouldHave(text("Справочник абонентов")).click();
            assertTrue($(tableSubscribersBy).isEnabled(), "Не удалось загрузить страницу 'Справочник абонентов'");
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public int checkSubscribersSIP(String login){
        $(searchSubscribersBy).clear();
        $(searchSubscribersBy).sendKeys(login);
        return $$(tableSubscribersBy).size();
    }

    @Step(value = "Добавляем SIP абонентов")
    public void setSubscribersSIP(String login, String portDx){
        $(searchSubscribersBy).clear();
        $(searchSubscribersBy).sendKeys(login);
        if($$(tableSubscribersBy).size() == 0) {
            $(buttonAddSubscriberBy).click();
            $(sipUserParamsTriggerBy).click();
            $(spUserNameBy).sendKeys(login);

            if( ! $(userNsIpBy).getText().equals(server)){
                $(userNsIpBy).clear();
                $(userNsIpBy).sendKeys(server);
            }

            if(! $(userNsPortBy).getText().equals(portNotify)){
                $(userNsPortBy).clear();
                $(userNsPortBy).sendKeys(portNotify);
            }

            $(spUserPasswordBy).sendKeys(password);
            $(spUserPwdConfirmBy).sendKeys(password);
            $(sipSpWrapHeaderBy).click();

            if( ! $(spanServIpBy).getText().equals(server)){
                $(spanServIpBy).clear();
                $(spanServIpBy).sendKeys(server);
            }

            if( ! $(spanServPortBy).getText().equals(portSip)){
                $(spanServPortBy).clear();
                $(spanServPortBy).sendKeys(portSip);
            }

            if( ! $(spanSpSipPortBy).getText().equals(portDx)) {
                $(spanSpSipPortBy).clear();
                $(spanSpSipPortBy).sendKeys(portDx);
            }

            $(spanNumberListBy).sendKeys(login);
            $(phoneBy).sendKeys(login);
            $(lastNameBy).sendKeys(login);
            $(buttonSaveBy).click();
            $(buttonNotEmailBy).click();
        }
    }
}
